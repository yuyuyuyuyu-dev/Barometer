package dev.yuyuyuyuyu.barometer.data.errors

import dev.yuyuyuyuyu.barometer.error.TraceAppenderDelegate
import dev.yuyuyuyuyu.barometer.error.TraceInfo

sealed interface BarometricPressureRepositoryError : DataError {
    override fun appendTrace(traceInfo: TraceInfo): BarometricPressureRepositoryError

    data class DeviceDoesNotHaveBarometricSensor(
        override val errorTrace: List<TraceInfo> = emptyList(),
    ) : BarometricPressureRepositoryError {
        override val message: String = "device does not have barometric sensor"

        override fun appendTrace(traceInfo: TraceInfo): DeviceDoesNotHaveBarometricSensor {
            return TraceAppenderDelegate(this.errorTrace) { newTrace ->
                this.copy(errorTrace = newTrace)
            }.append(traceInfo)
        }
    }
}
