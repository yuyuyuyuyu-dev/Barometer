package io.github.yukoba.barometer.domain.errors

import io.github.yukoba.barometer.data.errors.DataError
import io.github.yukoba.barometer.error.BaseError
import io.github.yukoba.barometer.error.TraceAppenderDelegate

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
