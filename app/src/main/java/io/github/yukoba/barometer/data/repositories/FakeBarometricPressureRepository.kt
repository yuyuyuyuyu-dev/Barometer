package io.github.yukoba.barometer.data.repositories

import io.github.yukoba.barometer.data.models.BarometricPressureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBarometricPressureRepository : BarometricPressureRepository {
    override val barometricPressure: Flow<Float> = flow { emit(149.731f) }
}
