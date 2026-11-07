package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.yuyuyuyuyu.barometer.R
import dev.yuyuyuyuyu.barometer.ui.barometer.model.BarometerState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Barometer(
    modifier: Modifier = Modifier,
    viewModel: BarometerViewModel = koinViewModel(),
) {
    val barometerState by viewModel.barometerState.collectAsStateWithLifecycle()

    Barometer(barometerState = barometerState, modifier = modifier)
}

@Composable
fun Barometer(
    barometerState: BarometerState,
    modifier: Modifier = Modifier,
) = Box(
    modifier =
        modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.screen_padding)),
    contentAlignment = Alignment.Center,
) {
    Text(
        text =
            when (barometerState) {
                is BarometerState.SuccessToGetPressure -> {
                    barometerState.pressure
                }

                is BarometerState.DeviceDoesNotHaveBarometricSensor -> {
                    stringResource(R.string.device_does_not_have_barometric_sensor_error)
                }

                is BarometerState.Loading -> {
                    stringResource(R.string.loading)
                }
            },
        style = MaterialTheme.typography.displaySmall,
    )
}

@Preview
@Composable
fun BarometerPreview(
    @PreviewParameter(BarometerPreviewParameterProvider::class) barometerState: BarometerState,
) {
    Barometer(barometerState = barometerState)
}

private class BarometerPreviewParameterProvider : PreviewParameterProvider<BarometerState> {
    override val values: Sequence<BarometerState> =
        sequenceOf(
            BarometerState.SuccessToGetPressure(pressure = "731.149 hPa"),
            BarometerState.DeviceDoesNotHaveBarometricSensor,
            BarometerState.Loading,
        )
}
