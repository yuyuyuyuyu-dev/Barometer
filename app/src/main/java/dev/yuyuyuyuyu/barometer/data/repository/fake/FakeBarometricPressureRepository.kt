package dev.yuyuyuyuyu.barometer.data.repository.fake

import dev.yuyuyuyuyu.barometer.data.model.BarometricPressureState
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeBarometricPressureRepository(
    firstPressure: BarometricPressureState,
) : BarometricPressureRepository {
    private val flow = MutableStateFlow(firstPressure)

    override val pressure = flow.asStateFlow()

    fun updatePressure(newValue: BarometricPressureState) {
        flow.value = newValue
    }
}
