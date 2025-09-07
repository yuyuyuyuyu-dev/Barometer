package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.michaelbull.result.fold
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import dev.yuyuyuyuyu.barometer.data.errors.BarometricPressureRepositoryError
import dev.yuyuyuyuyu.barometer.domain.errors.DomainError
import dev.yuyuyuyuyu.barometer.domain.useCase.GetFormattedBarometricPressureFlowUseCase
import dev.yuyuyuyuyu.barometer.error.TraceInfo
import dev.yuyuyuyuyu.barometer.ui.barometer.models.BarometerState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class BarometerPresenter(
    private val getFormattedBarometricPressureFlowUseCase: GetFormattedBarometricPressureFlowUseCase,
) : Presenter<BarometerScreen.State> {
    @Composable
    override fun present(): BarometerScreen.State {
        var barometerState: BarometerState by rememberRetained {
            mutableStateOf(BarometerState.Loading)
        }

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
            .onEach { state ->
                barometerState = state
            }
            .collectAsStateWithLifecycle(
                initialValue = barometerState,
            )

        return BarometerScreen.State(barometerState)
    }
}
