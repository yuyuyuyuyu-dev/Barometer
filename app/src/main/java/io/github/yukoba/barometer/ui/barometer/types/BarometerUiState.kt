package io.github.yukoba.barometer.ui.barometer.types

sealed interface BarometerUiState {
    data class Success(val pressure: String) : BarometerUiState
    data object Error : BarometerUiState
}
