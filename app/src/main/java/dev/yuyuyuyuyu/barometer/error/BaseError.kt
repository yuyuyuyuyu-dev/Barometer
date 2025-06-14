package dev.yuyuyuyuyu.barometer.error

interface BaseError {
    val errorTrace: List<TraceInfo>

    val message: String

    val messageWithTrace: String
        get() = "$message " + errorTrace.joinToString(" ") { "(${it.fileName}:${it.lineNumber})" }

    fun appendTrace(traceInfo: TraceInfo): BaseError
}
