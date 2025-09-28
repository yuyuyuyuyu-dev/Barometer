package dev.yuyuyuyuyu.barometer.data.error

import dev.yuyuyuyuyu.barometer.error.BaseError
import dev.yuyuyuyuyu.barometer.error.TraceInfo

sealed interface DataError : BaseError {
    override fun appendTrace(traceInfo: TraceInfo): DataError
}
