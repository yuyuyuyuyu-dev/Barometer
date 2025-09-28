package dev.yuyuyuyuyu.barometer.data.dataSource.impl

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import dev.yuyuyuyuyu.barometer.data.dataSource.BarometricPressureDataSource
import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import dev.yuyuyuyuyu.barometer.error.TraceInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class BarometricPressureDataSourceImpl(
    private val sensorManager: SensorManager,
) : BarometricPressureDataSource {
    private val barometricPressureSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
    }

    override val pressure = callbackFlow {
        if (barometricPressureSensor == null) {
            trySend(
                element = Err(
                    error = BarometricPressureError.DeviceDoesNotHaveBarometricSensor()
                        .appendTrace(TraceInfo.getCurrent()),
                ),
            )

            return@callbackFlow
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_PRESSURE) {
                    trySend(Ok(event.values.first()))
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // NOOP
            }
        }

        sensorManager.registerListener(
            listener,                          // listener: android.hardware.SensorEventListener
            barometricPressureSensor,          // sensor: android.hardware.Sensor
            SensorManager.SENSOR_DELAY_NORMAL, // samplingPeriodUs: int
        )

        awaitClose {
            Timber.d("awaitClose")
            sensorManager.unregisterListener(listener)
        }
    }
        .flowOn(Dispatchers.IO)
}
