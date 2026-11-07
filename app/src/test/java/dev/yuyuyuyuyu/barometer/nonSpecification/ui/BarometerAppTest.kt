package dev.yuyuyuyuyu.barometer.nonSpecification.ui

import android.app.Application
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.v2.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
class BarometerAppTest {
    @get:Rule
    val composeRule = createEmptyComposeRule()

    private val application = ApplicationProvider.getApplicationContext<Application>()

    private val sensorManager = shadowOf(application.getSystemService(SensorManager::class.java))

    private val sensor = SensorBuilder.newBuilder().setType(Sensor.TYPE_PRESSURE).build()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should show that it is loading until the sensor reports`() {
        // Arrange
        sensorManager.addSensor(sensor)

        // Act
        ActivityScenario.launch(MainActivity::class.java)

        // Assert
        composeRule.onNodeWithText("Loading...").assertExists()
    }

    @Test
    fun `should tell that the device has no barometer when it has none`() {
        // Act
        ActivityScenario.launch(MainActivity::class.java)

        // Assert
        composeRule.waitUntil(timeoutMillis = 3_000) {
            composeRule
                .onAllNodesWithText("This device does not have a barometric sensor.")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun `should keep showing the last pressure after the screen rotates`() {
        // Arrange
        sensorManager.addSensor(sensor)
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        composeRule.reportUntilShown(pressure = 1013.25f, text = "1013.25000 hPa")

        // Act
        RuntimeEnvironment.setQualifiers("+land")
        scenario.recreate()

        // Assert
        composeRule.onNodeWithText("1013.25000 hPa").assertExists()
    }

    @Test
    fun `should show the open source licenses from the menu`() {
        // Arrange
        sensorManager.addSensor(sensor)
        ActivityScenario.launch(MainActivity::class.java)

        // Act
        composeRule.onNodeWithContentDescription("menu").performClick()
        composeRule.onNodeWithText("Open source licenses").performClick()

        // Assert
        composeRule.onNodeWithText("Open source licenses").assertExists()
        composeRule.onNodeWithContentDescription("navigate back").assertExists()
        composeRule.onNodeWithText("Loading...").assertDoesNotExist()
    }

    @Test
    fun `should go back to the barometer from the open source licenses`() {
        // Arrange
        sensorManager.addSensor(sensor)
        ActivityScenario.launch(MainActivity::class.java)
        composeRule.onNodeWithContentDescription("menu").performClick()
        composeRule.onNodeWithText("Open source licenses").performClick()

        // Act
        composeRule.onNodeWithContentDescription("navigate back").performClick()

        // Assert
        composeRule.onNodeWithText("Barometer").assertExists()
        composeRule.onNodeWithText("Loading...").assertExists()
        composeRule.onNodeWithContentDescription("navigate back").assertDoesNotExist()
    }

    @Test
    fun `should keep the open source licenses after the screen rotates`() {
        // Arrange
        sensorManager.addSensor(sensor)
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        composeRule.onNodeWithContentDescription("menu").performClick()
        composeRule.onNodeWithText("Open source licenses").performClick()

        // Act
        RuntimeEnvironment.setQualifiers("+land")
        scenario.recreate()

        // Assert
        composeRule.onNodeWithText("Open source licenses").assertExists()
        composeRule.onNodeWithContentDescription("navigate back").assertExists()
    }

    @Test
    fun `should open the source code from the menu`() {
        // Arrange
        sensorManager.addSensor(sensor)
        ActivityScenario.launch(MainActivity::class.java)

        // Act
        composeRule.onNodeWithContentDescription("menu").performClick()
        composeRule.onNodeWithText("Source code").performClick()

        // Assert
        val intent = shadowOf(application).nextStartedActivity
        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals(Uri.parse("https://github.com/yuyuyuyuyu-dev/Barometer"), intent.data)
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
