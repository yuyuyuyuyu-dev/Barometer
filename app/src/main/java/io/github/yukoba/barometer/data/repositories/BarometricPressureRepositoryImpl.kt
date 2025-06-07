package io.github.yukoba.barometer.data.repositories

import io.github.yukoba.barometer.data.models.BarometricPressureRepository
import kotlinx.coroutines.flow.Flow

class BarometricPressureRepositoryImpl : BarometricPressureRepository {
    override val barometricPressure: Flow<Float> = TODO()
}
