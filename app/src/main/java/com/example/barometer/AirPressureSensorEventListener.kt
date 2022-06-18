package com.example.barometer

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AirPressureSensorEventListener(
    private val sensorManager: SensorManager,
    private val onSensorChanged: (value: String) -> Unit
) : SensorEventListener {

    private val airPressureSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // センサーの精度が変わったときの処理を記述する
        // NOOP
    }

    override fun onSensorChanged(event: SensorEvent) {
        onSensorChanged(event.values[0].toString())
    }

    fun registerAirPressureSensor() {
        sensorManager.registerListener(this, airPressureSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unregisterAirPressureSensor() {
        sensorManager.unregisterListener(this)
    }
}