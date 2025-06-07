package io.github.yukoba.barometer.ui.barometer

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object BarometerScreen : Screen {
    data class State(
        val pressure: String?,
    ) : CircuitUiState
}
