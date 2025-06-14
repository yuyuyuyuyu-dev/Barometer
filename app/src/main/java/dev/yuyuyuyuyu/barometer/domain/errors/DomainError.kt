package dev.yuyuyuyuyu.barometer.domain.errors

import dev.yuyuyuyuyu.barometer.data.errors.DataError
import dev.yuyuyuyuyu.barometer.error.BaseError
import dev.yuyuyuyuyu.barometer.error.TraceInfo

sealed interface DomainError : BaseError {
    override fun appendTrace(traceInfo: TraceInfo): DomainError

    data class FromDataLayer(
        val error: DataError,
    ) : DomainError {
        override val errorTrace: List<TraceInfo> = error.errorTrace
        override val message = error.message

        override fun appendTrace(traceInfo: TraceInfo): FromDataLayer {
            val newError = error.appendTrace(traceInfo)
            return this.copy(error = newError)
        }
    }
}
