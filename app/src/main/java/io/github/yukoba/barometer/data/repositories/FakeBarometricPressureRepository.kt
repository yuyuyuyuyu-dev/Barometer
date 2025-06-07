package io.github.yukoba.barometer.data.repositories

import io.github.yukoba.barometer.data.models.BarometricPressureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeBarometricPressureRepository(firstPressure: Float) : BarometricPressureRepository {
    private val flow = MutableStateFlow(firstPressure)
    override val barometricPressure: Flow<Float> = flow

    fun updatePressure(newValue: Float) {
        flow.value = newValue
    }
}
