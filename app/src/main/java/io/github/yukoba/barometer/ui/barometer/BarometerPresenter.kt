package io.github.yukoba.barometer.ui.barometer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.slack.circuit.runtime.presenter.Presenter
import io.github.yukoba.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase


class BarometerPresenter(
    private val getFormattedBarometricPressureFlowUseCase: GetFormattedBarometricPressureFlowUseCase,
) : Presenter<BarometerScreen.State> {
    @Composable
    override fun present(): BarometerScreen.State {
        val pressure by getFormattedBarometricPressureFlowUseCase()
            .collectAsStateWithLifecycle(initialValue = null)
        return BarometerScreen.State(pressure = pressure)
    }
}
