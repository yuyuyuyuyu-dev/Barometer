package io.github.yukoba.barometer.data.errors

import io.github.yukoba.barometer.error.BaseError
import io.github.yukoba.barometer.error.TraceAppenderDelegate

sealed interface BarometricPressureRepositoryError : DataError {
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
