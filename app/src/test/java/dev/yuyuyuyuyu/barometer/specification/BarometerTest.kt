package dev.yuyuyuyuyu.barometer.specification

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.v2.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.yuyuyuyuyu.barometer.MainActivity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.SensorBuilder
import org.robolectric.shadows.SensorEventBuilder

@RunWith(AndroidJUnit4::class)
class BarometerTest {
    @get:Rule
    val composeRule = createEmptyComposeRule()

    private val sensorManager =
        shadowOf(
            ApplicationProvider.getApplicationContext<Application>().getSystemService(SensorManager::class.java),
        )

    private val sensor = SensorBuilder.newBuilder().setType(Sensor.TYPE_PRESSURE).build()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should show the barometric pressure the sensor reports`() {
        // Arrange
        sensorManager.addSensor(sensor)
        ActivityScenario.launch(MainActivity::class.java)

        // Act
        composeRule.reportUntilShown(pressure = 1013.25f, text = "1013.25000 hPa")

        // Assert
        composeRule.onNodeWithText("1013.25000 hPa").assertExists()
    }

    @Test
    fun `should keep the flow of the sensor running while the screen rotates`() {
        // Arrange
        sensorManager.addSensor(sensor)
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        composeRule.reportUntilShown(pressure = 1013.25f, text = "1013.25000 hPa")
        val listenersBeforeRotation = sensorManager.listeners

        // Act
        RuntimeEnvironment.setQualifiers("+land")
        scenario.recreate()
        composeRule.reportUntilShown(pressure = 998.5f, text = "998.50000 hPa")

        // Assert
        assertEquals(listenersBeforeRotation, sensorManager.listeners)
    }

    private fun ComposeTestRule.reportUntilShown(
        pressure: Float,
        text: String,
    ) {
        waitUntil(timeoutMillis = 3_000) {
            sensorManager.sendSensorEventToListeners(
                SensorEventBuilder.newBuilder(sensor, floatArrayOf(pressure)).build(),
            )
            onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }
    }
}
