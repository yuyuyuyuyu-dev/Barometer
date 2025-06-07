package io.github.yukoba.barometer.data.repositories

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.github.yukoba.barometer.data.models.BarometricPressureRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emptyFlow

class BarometricPressureRepositoryImpl(
    private val sensorManager: SensorManager,
) : BarometricPressureRepository {
    private val barometricPressureSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
    }

    override val barometricPressure: Flow<Float> = if (barometricPressureSensor == null) {
        emptyFlow()
    } else {
        callbackFlow {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event?.sensor?.type == Sensor.TYPE_PRESSURE) {
                        trySend(event.values.first())
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
                sensorManager.unregisterListener(listener)
            }
        }
    }
}
