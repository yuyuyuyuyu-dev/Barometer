package dev.yuyuyuyuyu.barometer.data.errors

import dev.yuyuyuyuyu.barometer.error.BaseError
import dev.yuyuyuyuyu.barometer.error.TraceAppenderDelegate

sealed interface BarometricPressureRepositoryError : DataError {
    override fun appendTrace(): BarometricPressureRepositoryError

    data class DeviceDoesNotHaveBarometricSensor(
        override val errorTrace: List<BaseError.TraceInfo> = emptyList(),
    ) : BarometricPressureRepositoryError {
        override val message: String = "device does not have barometric sensor"

        override fun appendTrace(): DeviceDoesNotHaveBarometricSensor {
            return TraceAppenderDelegate(this.errorTrace) { newTrace ->
                this.copy(errorTrace = newTrace)
            }.append()
        }
    }
}
