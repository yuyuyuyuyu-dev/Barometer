package com.example.barometer

import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel(sensorManager: SensorManager) : ViewModel() {
    val airPressureLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val airPressureSensorEventListener = AirPressureSensorEventListener(sensorManager) {
        airPressureLiveData.value = it
    }

    fun registerAirPressureSensor() {
        airPressureSensorEventListener.registerAirPressureSensor()
    }

    fun unregisterAirPressureSensor() {
        airPressureSensorEventListener.unregisterAirPressureSensor()
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