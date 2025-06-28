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
import dev.yuyuyuyuyu.barometer.error.TraceInfo
import dev.yuyuyuyuyu.barometer.ui.barometer.models.BarometerState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class BarometerPresenter(
    private val getFormattedBarometricPressureFlowUseCase: GetFormattedBarometricPressureFlowUseCase,
) : Presenter<BarometerScreen.State>, ViewModel() {
    @Composable
    override fun present(): BarometerScreen.State {
        val barometerStateFlow = rememberRetained {
            getFormattedBarometricPressureFlowUseCase().fold(
                success = { pressureFlow ->
                    pressureFlow.map {
                        BarometerState.SuccessToGetPressure(it)
                    }
                },
                failure = { error ->
                    Timber.e(error.appendTrace(TraceInfo.getCurrent()).messageWithTrace)

                    when (error) {
                        is DomainError.FromDataLayer -> when (error.error) {
                            is BarometricPressureRepositoryError.DeviceDoesNotHaveBarometricSensor -> {
                                flowOf(BarometerState.DeviceDoesNotHaveBarometricSensor)
                            }
                        }
                    }
                },
            )
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = BarometerState.Loading,
                )
        }

        val pressureState by barometerStateFlow.collectAsStateWithLifecycle()
        return BarometerScreen.State(barometerState = pressureState)
    }
}
