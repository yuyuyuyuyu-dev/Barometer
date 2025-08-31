package dev.yuyuyuyuyu.barometer.data.repository.fake

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.data.errors.BarometricPressureRepositoryError
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
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