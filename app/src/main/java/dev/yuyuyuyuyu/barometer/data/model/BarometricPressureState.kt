package dev.yuyuyuyuyu.barometer.data.model

import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError

sealed interface BarometricPressureState {
    data object Loading : BarometricPressureState
    data class Success(val pressure: Float) : BarometricPressureState
    data class Failure(val error: BarometricPressureError) : BarometricPressureState
}
