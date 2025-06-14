package dev.yuyuyuyuyu.barometer.domain.errors

import dev.yuyuyuyuyu.barometer.data.errors.DataError
import dev.yuyuyuyuyu.barometer.error.BaseError

sealed interface DomainError : BaseError {
    override fun appendTrace(): DomainError

    data class FromDataLayer(val error: DataError) : DomainError {
        override val errorTrace: List<BaseError.TraceInfo> = error.errorTrace
        override val message = error.message

        override fun appendTrace(): FromDataLayer {
            val newError = error.appendTrace()
            return this.copy(error = newError)
        }
    }
}
