package dev.yuyuyuyuyu.barometer.data.error

import dev.yuyuyuyuyu.barometer.error.TraceAppenderDelegate
import dev.yuyuyuyuyu.barometer.error.TraceInfo

sealed interface BarometricPressureError : DataError {
    override fun appendTrace(traceInfo: TraceInfo): BarometricPressureError

    data class DeviceDoesNotHaveBarometricSensor(
        override val errorTrace: List<TraceInfo> = emptyList(),
    ) : BarometricPressureError {
        override val message: String = "device does not have barometric sensor"

        override fun appendTrace(traceInfo: TraceInfo): DeviceDoesNotHaveBarometricSensor {
            return TraceAppenderDelegate(this.errorTrace) { newTrace ->
                this.copy(errorTrace = newTrace)
            }.append(traceInfo)
        }
    }
}
