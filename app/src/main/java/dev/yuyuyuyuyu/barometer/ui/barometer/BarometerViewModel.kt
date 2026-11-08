package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yuyuyuyuyu.barometer.data.model.BarometricPressureState
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import dev.yuyuyuyuyu.barometer.domain.useCase.FormatPressureUseCase
import dev.yuyuyuyuyu.barometer.ui.barometer.model.BarometerState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BarometerViewModel(
    private val formatPressureUseCase: FormatPressureUseCase,
    barometricPressureRepository: BarometricPressureRepository,
) : ViewModel() {
    val barometerState: StateFlow<BarometerState> =
        barometricPressureRepository.pressure
            .map(::toBarometerState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = toBarometerState(barometricPressureRepository.pressure.value),
            )

    private fun toBarometerState(pressureState: BarometricPressureState) =
        when (pressureState) {
            BarometricPressureState.Loading -> {
                BarometerState.Loading
            }

            is BarometricPressureState.Success -> {
                BarometerState.SuccessToGetPressure(
                    pressure = formatPressureUseCase(pressure = pressureState.pressure),
                )
            }

            is BarometricPressureState.Failure -> {
                BarometerState.DeviceDoesNotHaveBarometricSensor
            }
        }
}
