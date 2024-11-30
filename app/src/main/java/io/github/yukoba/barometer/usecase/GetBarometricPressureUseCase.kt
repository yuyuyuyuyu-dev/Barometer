package io.github.yukoba.barometer.usecase

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class GetBarometricPressureUseCase(
    private val sensorManager: SensorManager,
    private val onBarometricPressureChanged: (Float) -> Unit,
) : SensorEventListener {
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    override fun onSensorChanged(event: SensorEvent?) {
        val pressure = event?.values?.first() ?: return

        onBarometricPressureChanged(pressure)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // NOOP
    }

    fun enable() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun disable() {
        sensorManager.unregisterListener(this)
    }
}