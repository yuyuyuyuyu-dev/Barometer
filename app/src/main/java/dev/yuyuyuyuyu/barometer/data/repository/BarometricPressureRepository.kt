package dev.yuyuyuyuyu.barometer.data.repository

import dev.yuyuyuyuyu.barometer.data.model.BarometricPressureState
import kotlinx.coroutines.flow.StateFlow

interface BarometricPressureRepository {
    val pressure: StateFlow<BarometricPressureState>
}
