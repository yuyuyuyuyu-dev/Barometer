package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.slack.circuit.runtime.presenter.Presenter
import dev.yuyuyuyuyu.barometer.data.model.BarometricPressureState
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import dev.yuyuyuyuyu.barometer.domain.useCase.FormatPressureUseCase
import dev.yuyuyuyuyu.barometer.ui.barometer.model.BarometerState

class BarometerPresenter(
    private val formatPressureUseCase: FormatPressureUseCase,
    private val barometricPressureRepository: BarometricPressureRepository,
) : Presenter<BarometerScreen.State> {
    @Composable
    override fun present(): BarometerScreen.State {
        val barometricPressureState by barometricPressureRepository.pressure.collectAsStateWithLifecycle()

        return BarometerScreen.State(
            barometerState = when (val pressureState = barometricPressureState) {
                BarometricPressureState.Loading -> BarometerState.Loading

                is BarometricPressureState.Success -> BarometerState.SuccessToGetPressure(
                    pressure = formatPressureUseCase(pressure = pressureState.pressure),
                )

                is BarometricPressureState.Failure -> BarometerState.DeviceDoesNotHaveBarometricSensor
            }
        )
    }
}
