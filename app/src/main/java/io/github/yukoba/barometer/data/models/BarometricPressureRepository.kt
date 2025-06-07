package io.github.yukoba.barometer.data.models

import kotlinx.coroutines.flow.Flow

interface BarometricPressureRepository {
    val barometricPressure: Flow<Float>
}
