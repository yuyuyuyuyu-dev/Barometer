package dev.yuyuyuyuyu.barometer.error

data class TraceInfo(
    val fileName: String,
    val lineNumber: Int,
) {
    companion object {
        fun getCurrent(): TraceInfo {
            // This Throwable is never thrown; it only exists to capture the current stack trace.
            @Suppress("ThrowingExceptionsWithoutMessageOrCause")
            val stackTrace = Throwable().stackTrace
            val element = stackTrace.getOrNull(1)!!

            return TraceInfo(
                fileName = element.fileName,
                lineNumber = element.lineNumber,
            )
        }
    }
}
