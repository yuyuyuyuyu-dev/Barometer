package dev.yuyuyuyuyu.barometer.ui.barometer

import app.cash.turbine.test
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import dev.yuyuyuyuyu.barometer.data.dataSource.fake.FakeBarometricPressureDataSource
import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import dev.yuyuyuyuyu.barometer.data.repository.impl.BarometricPressureRepositoryImpl
import dev.yuyuyuyuyu.barometer.domain.useCase.impl.FormatPressureUseCaseImpl
import dev.yuyuyuyuyu.barometer.testRule.MainDispatcherRule
import dev.yuyuyuyuyu.barometer.ui.barometer.model.BarometerState
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BarometerViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dataSource = FakeBarometricPressureDataSource()

    @Test
    fun `should start in the loading state before the sensor reports anything`() =
        runTest {
            // Arrange
            val viewModel = createViewModel()

            viewModel.barometerState.test {
                // Act
                val actual = awaitItem()

                // Assert
                assertEquals(BarometerState.Loading, actual)
            }
        }

    @Test
    fun `should show the formatted pressure once the sensor reports a reading`() =
        runTest {
            // Arrange
            val viewModel = createViewModel()

            viewModel.barometerState.test {
                skipItems(1)

                // Act
                dataSource.emit(Ok(1013.25f))

                // Assert
                assertEquals(BarometerState.SuccessToGetPressure(pressure = "1013.25000 hPa"), awaitItem())
            }
        }

    @Test
    fun `should follow the sensor when it reports a new reading`() =
        runTest {
            // Arrange
            val viewModel = createViewModel()

            viewModel.barometerState.test {
                skipItems(1)
                dataSource.emit(Ok(1013.25f))
                skipItems(1)

                // Act
                dataSource.emit(Ok(998.5f))

                // Assert
                assertEquals(BarometerState.SuccessToGetPressure(pressure = "998.50000 hPa"), awaitItem())
            }
        }

    @Test
    fun `should report a missing sensor when the data source fails`() =
        runTest {
            // Arrange
            val viewModel = createViewModel()

            viewModel.barometerState.test {
                skipItems(1)

                // Act
                dataSource.emit(Err(BarometricPressureError.DeviceDoesNotHaveBarometricSensor()))

                // Assert
                assertEquals(BarometerState.DeviceDoesNotHaveBarometricSensor, awaitItem())
            }
        }

    private fun TestScope.createViewModel() =
        BarometerViewModel(
            formatPressureUseCase = FormatPressureUseCaseImpl(),
            barometricPressureRepository =
                BarometricPressureRepositoryImpl(
                    barometricPressureDataSource = dataSource,
                    ioScope = backgroundScope,
                ),
        )
}
