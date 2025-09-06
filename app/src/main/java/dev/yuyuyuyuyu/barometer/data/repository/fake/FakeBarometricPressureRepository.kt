package dev.yuyuyuyuyu.barometer.data.repository.fake

import com.github.michaelbull.result.Ok
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import kotlinx.coroutines.flow.MutableStateFlow

class FakeBarometricPressureRepository(firstPressure: Float) : BarometricPressureRepository {
    private val flow = MutableStateFlow(firstPressure)

    override val pressure = Ok(flow)

    fun updatePressure(newValue: Float) {
        flow.value = newValue
    }
}