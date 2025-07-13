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
import dev.yuyuyuyuyu.barometer.R
import dev.yuyuyuyuyu.barometer.ui.barometer.models.BarometerState

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
fun SuccessBarometerPreview() {
    Barometer(
        state = BarometerScreen.State(
            barometerState = BarometerState.SuccessToGetPressure("731.149 hPa"),
        ),
    )
}

@Preview
@Composable
fun FailureBarometerPreview() {
    Barometer(
        state = BarometerScreen.State(
            barometerState = BarometerState.DeviceDoesNotHaveBarometricSensor,
        ),
    )
}

@Preview
@Composable
fun LoadingBarometerPreview() {
    Barometer(
        state = BarometerScreen.State(
            barometerState = BarometerState.Loading,
        ),
    )
}
