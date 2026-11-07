package dev.yuyuyuyuyu.barometer.nonSpecification.data.dataSource

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.getError
import dev.yuyuyuyuyu.barometer.data.dataSource.impl.BarometricPressureDataSourceImpl
import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.SensorBuilder
import org.robolectric.shadows.SensorEventBuilder

@RunWith(AndroidJUnit4::class)
class BarometricPressureDataSourceImplTest {
    private val sensorManager =
        ApplicationProvider.getApplicationContext<Application>().getSystemService(SensorManager::class.java)

    private val sensor = SensorBuilder.newBuilder().setType(Sensor.TYPE_PRESSURE).build()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should report a missing sensor when the device has no barometer`() =
        runTest {
            // Arrange
            val dataSource = BarometricPressureDataSourceImpl(sensorManager = sensorManager)

            dataSource.pressure.test {
                // Act
                val actual = awaitItem().getError()

                // Assert
                assertTrue(actual is BarometricPressureError.DeviceDoesNotHaveBarometricSensor)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `should finish without an exception when the device has no barometer`() =
        runTest {
            // Arrange
            val dataSource = BarometricPressureDataSourceImpl(sensorManager = sensorManager)

            dataSource.pressure.test {
                skipItems(1)

                // Act
                awaitComplete()
            }
        }

    @Test
    fun `should emit the pressure the sensor reports`() =
        runTest {
            // Arrange
            shadowOf(sensorManager).addSensor(sensor)
            val dataSource = BarometricPressureDataSourceImpl(sensorManager = sensorManager)

            dataSource.pressure.test {
                awaitListeners { it.isNotEmpty() }

                // Act
                report(pressure = 1013.25f)

                // Assert
                assertEquals(Ok(1013.25f), awaitItem())
            }
        }

    @Test
    fun `should stop listening to the sensor when the collection stops`() =
        runTest {
            // Arrange
            shadowOf(sensorManager).addSensor(sensor)
            val dataSource = BarometricPressureDataSourceImpl(sensorManager = sensorManager)

            dataSource.pressure.test {
                awaitListeners { it.isNotEmpty() }

                // Act
                cancel()
            }

            // Assert
            awaitListeners { it.isEmpty() }
        }

    private fun report(pressure: Float) {
        shadowOf(sensorManager).sendSensorEventToListeners(
            SensorEventBuilder.newBuilder(sensor, floatArrayOf(pressure)).build(),
        )
    }

    private fun awaitListeners(condition: (List<*>) -> Boolean) {
        val deadline = System.currentTimeMillis() + 3_000
        while (!condition(shadowOf(sensorManager).listeners)) {
            check(System.currentTimeMillis() < deadline) { "The listeners never met the condition." }
            Thread.sleep(10)
        }
    }
}
