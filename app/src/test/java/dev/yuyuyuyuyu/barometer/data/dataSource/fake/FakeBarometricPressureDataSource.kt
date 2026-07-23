package dev.yuyuyuyuyu.barometer.data.dataSource.fake

import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.data.dataSource.BarometricPressureDataSource
import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Stands in for the real sensor, which is the only thing this app talks to outside its own process.
 * Everything above this boundary — repository, use case, presenter — runs for real in the tests.
 */
class FakeBarometricPressureDataSource : BarometricPressureDataSource {
    private val flow = MutableSharedFlow<Result<Float, BarometricPressureError>>(replay = 1)

    override val pressure = flow.asSharedFlow()

    suspend fun emit(reading: Result<Float, BarometricPressureError>) {
        flow.emit(reading)
    }
}
