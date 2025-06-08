package dev.yuyuyuyuyu.barometer.data.models

import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.data.errors.BarometricPressureRepositoryError
import kotlinx.coroutines.flow.Flow

interface BarometricPressureRepository {
    fun getBarometricPressureFlow(): Result<Flow<Float>, BarometricPressureRepositoryError>
}
