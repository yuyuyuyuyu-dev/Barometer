package dev.yuyuyuyuyu.barometer.data.repository.impl

import com.github.michaelbull.result.fold
import dev.yuyuyuyuyu.barometer.data.dataSource.BarometricPressureDataSource
import dev.yuyuyuyuyu.barometer.data.model.BarometricPressureState
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BarometricPressureRepositoryImpl(
    barometricPressureDataSource: BarometricPressureDataSource,
    ioScope: CoroutineScope,
) : BarometricPressureRepository {
    override val pressure = barometricPressureDataSource.pressure
        .map { result ->
            result.fold(
                success = { BarometricPressureState.Success(pressure = it) },
                failure = { BarometricPressureState.Failure(error = it) },
            )
        }
        .stateIn(
            scope = ioScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BarometricPressureState.Loading,
        )
}
