package dev.yuyuyuyuyu.barometer.domain.errors

import dev.yuyuyuyuyu.barometer.data.errors.DataError
import dev.yuyuyuyuyu.barometer.error.BaseError
import dev.yuyuyuyuyu.barometer.error.TraceAppenderDelegate

sealed interface DomainError : BaseError {
    data class FromDataLayer(
        val error: DataError,
        override val errorTrace: List<BaseError.TraceInfo> = error.errorTrace,
    ) : DomainError {
        override val message = error.message

        override fun appendTrace(): FromDataLayer {
            return TraceAppenderDelegate(this.errorTrace) { newTrace ->
                this.copy(errorTrace = newTrace)
            }.append()
        }
    }
}
