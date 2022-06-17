package com.example.barometer

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel(private val sensorManager: SensorManager) : ViewModel(), SensorEventListener {
    val airPressureLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val airPressureSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    override fun onSensorChanged(event: SensorEvent) {
        airPressureLiveData.value = event.values[0].toString()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // センサーの精度が変わったときの処理を記述する
        // NOOP
    }

    fun registerAirPressureSensor() {
        sensorManager.registerListener(this, airPressureSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unregisterAirPressureSensor() {
        sensorManager.unregisterListener(this)
    }

    class Factory(private val sensorManager: SensorManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(sensorManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}