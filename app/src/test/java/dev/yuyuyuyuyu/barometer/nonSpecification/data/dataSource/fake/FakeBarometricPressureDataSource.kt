package dev.yuyuyuyuyu.barometer.nonSpecification.data.dataSource.fake

import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.data.dataSource.BarometricPressureDataSource
import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

class FakeBarometricPressureDataSource : BarometricPressureDataSource {
    private val flow = MutableSharedFlow<Result<Float, BarometricPressureError>>(replay = 1)

    override val pressure = flow.asSharedFlow()

    val subscriptionCount: StateFlow<Int> = flow.subscriptionCount

    suspend fun emit(reading: Result<Float, BarometricPressureError>) {
        flow.emit(reading)
    }
}
