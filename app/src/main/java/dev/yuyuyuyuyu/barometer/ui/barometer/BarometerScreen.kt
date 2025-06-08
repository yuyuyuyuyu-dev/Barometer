package dev.yuyuyuyuyu.barometer.ui.barometer

import com.github.michaelbull.result.Result
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.yuyuyuyuyu.barometer.domain.errors.DomainError
import kotlinx.parcelize.Parcelize

@Parcelize
data object BarometerScreen : Screen {
    data class State(
        val pressure: Result<String?, DomainError>,
    ) : CircuitUiState
}
