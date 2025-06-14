package dev.yuyuyuyuyu.barometer.ui.barometer.models

sealed interface PressureState {
    data class Success(val pressure: String) : PressureState
    data object Failure : PressureState
    data object Loading : PressureState
}
