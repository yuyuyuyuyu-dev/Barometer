package dev.yuyuyuyuyu.barometer.data.dataSource

import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import kotlinx.coroutines.flow.Flow

interface BarometricPressureDataSource {
    val pressure: Flow<Result<Float, BarometricPressureError>>
}
