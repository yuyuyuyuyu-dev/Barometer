package io.github.yukoba.barometer.ui.barometer

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.yukoba.barometer.data.repositories.BarometricPressureRepositoryImpl
import io.github.yukoba.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import io.github.yukoba.barometer.domain.useCases.GetFormattedBarometricPressureFlowUseCaseImpl
import io.github.yukoba.barometer.ui.barometer.types.BarometerUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BarometerViewModel(application: Application) : AndroidViewModel(application) {
    private val getFormattedBarometricPressureFlowUseCase: GetFormattedBarometricPressureFlowUseCase

    init {
        val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val barometricPressureRepository = BarometricPressureRepositoryImpl(
            sensorManager = sensorManager,
        )

        getFormattedBarometricPressureFlowUseCase = GetFormattedBarometricPressureFlowUseCaseImpl(
            barometricPressureRepository = barometricPressureRepository,
        )
    }

    val uiState: StateFlow<BarometerUiState> = getFormattedBarometricPressureFlowUseCase()
        .map { pressure ->
            BarometerUiState.Success(pressure = pressure)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = BarometerUiState.Error,
        )
}
