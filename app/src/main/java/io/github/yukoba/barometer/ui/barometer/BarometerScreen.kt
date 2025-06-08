package io.github.yukoba.barometer.ui.barometer

import com.github.michaelbull.result.Result
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import io.github.yukoba.barometer.domain.errors.DomainError
import kotlinx.parcelize.Parcelize

@Parcelize
data object BarometerScreen : Screen {
    data class State(
        val pressure: Result<String?, DomainError>,
    ) : CircuitUiState
}
