package dev.yuyuyuyuyu.barometer.ui.openSourceLicenseList

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object OpenSourceLicenseListScreen : Screen {
    data object State : CircuitUiState
}
