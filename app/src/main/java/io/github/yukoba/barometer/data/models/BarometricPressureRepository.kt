package io.github.yukoba.barometer.data.models

import com.github.michaelbull.result.Result
import io.github.yukoba.barometer.data.errors.BarometricPressureRepositoryError
import kotlinx.coroutines.flow.Flow

interface BarometricPressureRepository {
    fun getBarometricPressureFlow(): Result<Flow<Float>, BarometricPressureRepositoryError>
}
