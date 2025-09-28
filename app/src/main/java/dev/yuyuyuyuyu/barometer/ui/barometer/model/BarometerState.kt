package dev.yuyuyuyuyu.barometer.ui.barometer.model

sealed interface BarometerState {
    data class SuccessToGetPressure(val pressure: String) : BarometerState
    data object DeviceDoesNotHaveBarometricSensor : BarometerState
    data object Loading : BarometerState
}
