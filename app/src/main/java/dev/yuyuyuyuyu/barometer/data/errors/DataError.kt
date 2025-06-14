package dev.yuyuyuyuyu.barometer.data.errors

import dev.yuyuyuyuyu.barometer.error.BaseError

sealed interface DataError : BaseError {
    override fun appendTrace(): DataError
}
