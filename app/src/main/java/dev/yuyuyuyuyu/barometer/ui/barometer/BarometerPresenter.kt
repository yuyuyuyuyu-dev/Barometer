package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import dev.yuyuyuyuyu.barometer.data.errors.BarometricPressureRepositoryError
import dev.yuyuyuyuyu.barometer.domain.errors.DomainError
import dev.yuyuyuyuyu.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import dev.yuyuyuyuyu.barometer.ui.barometer.models.PressureState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BarometerPresenter(
    private val getFormattedBarometricPressureFlowUseCase: GetFormattedBarometricPressureFlowUseCase,
) : Presenter<BarometerScreen.State>, ViewModel() {
    @Composable
    override fun present(): BarometerScreen.State {
        val pressureStateFlow = rememberRetained {
            getFormattedBarometricPressureFlowUseCase().fold(
                success = { pressureFlow ->
                    pressureFlow.map {
                        PressureState.Success(it)
                    }
                },
                failure = { error ->
                    when (error) {
                        is DomainError.FromDataLayer -> when (error.error) {
                            is BarometricPressureRepositoryError.DeviceDoesNotHaveBarometricSensor -> {
                                flowOf(PressureState.Failure)
                            }
                        }
                    }
                },
            )
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = PressureState.Loading,
                )
        }

        val pressureState by pressureStateFlow.collectAsStateWithLifecycle()
        return BarometerScreen.State(pressure = pressureState)
    }
}
