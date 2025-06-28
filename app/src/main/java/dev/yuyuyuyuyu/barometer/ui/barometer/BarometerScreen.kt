package dev.yuyuyuyuyu.barometer.ui.barometer

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.yuyuyuyuyu.barometer.ui.barometer.models.BarometerState
import kotlinx.parcelize.Parcelize

@Parcelize
data object BarometerScreen : Screen {
    data class State(
        val barometerState: BarometerState,
    ) : CircuitUiState
}
