package io.github.yukoba.barometer.ui.barometer

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import io.github.yukoba.barometer.ui.barometer.types.BarometerUiState
import io.github.yukoba.barometer.usecase.GetBarometricPressureUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BarometerViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(BarometerUiState())
    val uiState: StateFlow<BarometerUiState> = _uiState.asStateFlow()

    private val getBarometricPressureUseCase = GetBarometricPressureUseCase(
        sensorManager = getApplication<Application>().applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager,
        onBarometricPressureChanged = ::onBarometricPressureChanged,
    )

    fun onPaused() {
        getBarometricPressureUseCase.disable()
    }

    fun onResumed() {
        getBarometricPressureUseCase.enable()
    }

    private fun onBarometricPressureChanged(pressure: Float) {
        _uiState.update { currentState ->
            currentState.copy(barometricPressure = pressure)
        }
    }
}