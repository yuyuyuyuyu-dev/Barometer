package io.github.yukoba.barometer.error

import io.github.yukoba.barometer.error.BaseError.TraceInfo

class TraceAppenderDelegate<T : BaseError>(
    private val currentErrorTrace: List<TraceInfo>,
    private val copyFunction: (newErrorTrace: List<TraceInfo>) -> T
) {
    fun append(): T {
        val stackTrace = Throwable().stackTrace
        val element = stackTrace.getOrNull(3)!!

        val traceInfo = TraceInfo(
            fileName = element.fileName,
            lineNumber = element.lineNumber,
        )

        return copyFunction(listOf(traceInfo) + currentErrorTrace)
    }
}
