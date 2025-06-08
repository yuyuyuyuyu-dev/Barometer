package io.github.yukoba.barometer.data.repositories

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.github.yukoba.barometer.data.errors.BarometricPressureRepositoryError
import io.github.yukoba.barometer.data.models.BarometricPressureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeBarometricPressureRepository(firstPressure: Float) : BarometricPressureRepository {
    private val flow = MutableStateFlow(firstPressure)

    override fun getBarometricPressureFlow(): Result<Flow<Float>, BarometricPressureRepositoryError> {
        return Ok(flow)
    }

    fun updatePressure(newValue: Float) {
        flow.value = newValue
    }
}
