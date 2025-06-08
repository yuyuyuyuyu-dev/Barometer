package io.github.yukoba.barometer.error

interface BaseError {
    val errorTrace: List<TraceInfo>

    val message: String

    val messageWithTrace: String
        get() = "$message " + errorTrace.joinToString(" ") { "($it)" }

    fun appendTrace(): BaseError

    data class TraceInfo(
        val fileName: String,
        val lineNumber: Int,
    )
}
