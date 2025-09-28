package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.yuyuyuyuyu.barometer.R
import dev.yuyuyuyuyu.barometer.ui.barometer.model.BarometerState

@Composable
fun Barometer(state: BarometerScreen.State, modifier: Modifier = Modifier) = Box(
    modifier = modifier
        .fillMaxSize()
        .padding(dimensionResource(R.dimen.screen_padding)),
    contentAlignment = Alignment.Center,
) {
    Text(
        text = when (state.barometerState) {
            is BarometerState.SuccessToGetPressure -> state.barometerState.pressure
            is BarometerState.DeviceDoesNotHaveBarometricSensor -> stringResource(R.string.device_does_not_have_barometric_sensor_error)
            is BarometerState.Loading -> stringResource(R.string.loading)
        },
        style = MaterialTheme.typography.displaySmall,
    )
}

@Preview
@Composable
private fun BarometerPreview(
    @PreviewParameter(BarometerPreviewParameterProvider::class) state: BarometerScreen.State,
) {
    Barometer(state)
}

private class BarometerPreviewParameterProvider : PreviewParameterProvider<BarometerScreen.State> {
    override val values: Sequence<BarometerScreen.State> = sequenceOf(
        BarometerScreen.State(
            BarometerState.SuccessToGetPressure(pressure = "731.149 hPa"),
        ),
        BarometerScreen.State(BarometerState.DeviceDoesNotHaveBarometricSensor),
        BarometerScreen.State(BarometerState.Loading),
    )
}
