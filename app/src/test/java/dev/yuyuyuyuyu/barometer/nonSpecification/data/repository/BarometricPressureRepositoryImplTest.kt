package dev.yuyuyuyuyu.barometer.nonSpecification.data.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import dev.yuyuyuyuyu.barometer.data.model.BarometricPressureState
import dev.yuyuyuyuyu.barometer.data.repository.impl.BarometricPressureRepositoryImpl
import dev.yuyuyuyuyu.barometer.nonSpecification.data.dataSource.fake.FakeBarometricPressureDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BarometricPressureRepositoryImplTest {
    private val dataSource = FakeBarometricPressureDataSource()

    @Test
    fun `should start in the loading state before the sensor reports anything`() =
        runTest {
            // Arrange
            val repository = createRepository()

            // Act
            val actual = repository.pressure.value

            // Assert
            assertEquals(BarometricPressureState.Loading, actual)
        }

    @Test
    fun `should hold the pressure the sensor reports`() =
        runTest {
            // Arrange
            val repository = createRepository()
            backgroundScope.launch { repository.pressure.collect {} }
            runCurrent()

            // Act
            dataSource.emit(Ok(1013.25f))

            // Assert
            assertEquals(
                BarometricPressureState.Success(pressure = 1013.25f),
                repository.pressure.first { it != BarometricPressureState.Loading },
            )
        }

    @Test
    fun `should hold the failure the sensor reports`() =
        runTest {
            // Arrange
            val repository = createRepository()
            backgroundScope.launch { repository.pressure.collect {} }
            runCurrent()
            val error = BarometricPressureError.DeviceDoesNotHaveBarometricSensor()

            // Act
            dataSource.emit(Err(error))

            // Assert
            assertEquals(
                BarometricPressureState.Failure(error = error),
                repository.pressure.first { it != BarometricPressureState.Loading },
            )
        }

    @Test
    fun `should keep the sensor subscribed for 5 seconds after the last collector leaves`() =
        runTest {
            // Arrange
            val repository = createRepository()
            val collector = launch { repository.pressure.collect {} }
            runCurrent()

            // Act
            collector.cancel()
            advanceTimeBy(4_999)
            runCurrent()

            // Assert
            assertEquals(1, dataSource.subscriptionCount.value)
        }

    @Test
    fun `should release the sensor 5 seconds after the last collector leaves`() =
        runTest {
            // Arrange
            val repository = createRepository()
            val collector = launch { repository.pressure.collect {} }
            runCurrent()

            // Act
            collector.cancel()
            advanceTimeBy(5_000)
            runCurrent()

            // Assert
            assertEquals(0, dataSource.subscriptionCount.value)
        }

    private fun TestScope.createRepository() =
        BarometricPressureRepositoryImpl(
            barometricPressureDataSource = dataSource,
            ioScope = backgroundScope,
        )
}
