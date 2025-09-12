package dev.yuyuyuyuyu.barometer.data.repository.impl

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.data.errors.BarometricPressureRepositoryError
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import dev.yuyuyuyuyu.barometer.error.TraceInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn

class BarometricPressureRepositoryImpl(
    private val sensorManager: SensorManager,
    ioScope: CoroutineScope,
) : BarometricPressureRepository {
    private val barometricPressureSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
    }

    override val pressure: Result<Flow<Float>, BarometricPressureRepositoryError> =
        if (barometricPressureSensor == null) {
            Err(
                BarometricPressureRepositoryError.DeviceDoesNotHaveBarometricSensor()
                    .appendTrace(TraceInfo.Companion.getCurrent())
            )
        } else {
            Ok(
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
                    .flowOn(Dispatchers.IO)
                    .shareIn(
                        scope = ioScope,
                        started = SharingStarted.WhileSubscribed(5000),
                    )
            )
        }
}
