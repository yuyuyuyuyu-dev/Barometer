package dev.yuyuyuyuyu.barometer.data.repository

import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.data.errors.BarometricPressureRepositoryError
import kotlinx.coroutines.flow.Flow

interface BarometricPressureRepository {
    val pressure: Result<Flow<Float>, BarometricPressureRepositoryError>
}
