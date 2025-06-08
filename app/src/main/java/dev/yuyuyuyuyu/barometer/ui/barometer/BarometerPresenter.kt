package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.slack.circuit.runtime.presenter.Presenter
import dev.yuyuyuyuyu.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase

class BarometerPresenter(
    private val getFormattedBarometricPressureFlowUseCase: GetFormattedBarometricPressureFlowUseCase,
) : Presenter<BarometerScreen.State> {
    @Composable
    override fun present(): BarometerScreen.State {
        val result = getFormattedBarometricPressureFlowUseCase()
        if (result.isErr) {
            return BarometerScreen.State(pressure = Err(result.error))
        }
        val pressure by result.value
            .collectAsStateWithLifecycle(initialValue = null)
        return BarometerScreen.State(pressure = Ok(pressure))
    }
}
