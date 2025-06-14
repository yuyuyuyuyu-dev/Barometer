package dev.yuyuyuyuyu.barometer.error

data class TraceInfo(
    val fileName: String,
    val lineNumber: Int,
) {
    companion object {
        fun getCurrent(): TraceInfo {
            val stackTrace = Throwable().stackTrace
            val element = stackTrace.getOrNull(1)!!

            return TraceInfo(
                fileName = element.fileName,
                lineNumber = element.lineNumber,
            )
        }
    }
}
