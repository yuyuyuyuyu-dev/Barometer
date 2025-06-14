package dev.yuyuyuyuyu.barometer.error

class TraceAppenderDelegate<T : BaseError>(
    private val currentErrorTrace: List<TraceInfo>,
    private val copyFunction: (newErrorTrace: List<TraceInfo>) -> T
) {
    fun append(traceInfo: TraceInfo): T = copyFunction(
        listOf(traceInfo) + currentErrorTrace,
    )
}
