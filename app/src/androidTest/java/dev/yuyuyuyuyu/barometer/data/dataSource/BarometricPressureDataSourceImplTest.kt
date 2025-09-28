package dev.yuyuyuyuyu.barometer.data.dataSource

import android.content.Context
import android.hardware.SensorManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.michaelbull.result.unwrap
import dev.yuyuyuyuyu.barometer.data.dataSource.impl.BarometricPressureDataSourceImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BarometricPressureDataSourceImplTest {
    private lateinit var dataSource: BarometricPressureDataSourceImpl

    private lateinit var sensorManager: SensorManager

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        dataSource = BarometricPressureDataSourceImpl(sensorManager = sensorManager)
    }

    @Test
    // this test success if device has barometric sensor
    fun barometricPressure_emits_at_least_one_value() = runTest {
        val firstValue = dataSource.pressure.first().unwrap()

        // The value range is derived from the local atmospheric pressure at the highest and lowest elevations on Earth,
        // factoring in fluctuations from weather phenomena.
        // The fluctuation value is based on the difference between the record highest/lowest sea-level pressures
        // and the standard atmospheric pressure.
        assert(firstValue in 157.0..1156.8) {
            "Expected pressure to be within the valid terrestrial range (157.0..1156.8), but it was $firstValue."
        }
    }
}
