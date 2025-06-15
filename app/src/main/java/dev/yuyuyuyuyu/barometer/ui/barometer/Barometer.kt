package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.yuyuyuyuyu.barometer.R
import dev.yuyuyuyuyu.barometer.ui.barometer.models.PressureState

@Composable
fun Barometer(state: BarometerScreen.State, modifier: Modifier = Modifier) = Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
) {
    Text(
        text = when (state.pressure) {
            is PressureState.Success -> state.pressure.pressure
            is PressureState.Failure -> stringResource(R.string.could_not_get_barometric_sensor_value)
            is PressureState.Loading -> stringResource(R.string.loading)
        },
        fontSize = 34.sp,
        lineHeight = 51.sp,
    )
}

@Preview
@Composable
fun SuccessBarometerPreview() {
    Barometer(
        state = BarometerScreen.State(
            pressure = PressureState.Success("731.149 hPa"),
        ),
    )
}

@Preview
@Composable
fun FailureBarometerPreview() {
    Barometer(
        state = BarometerScreen.State(
            pressure = PressureState.Failure,
        ),
    )
}

@Preview
@Composable
fun LoadingBarometerPreview() {
    Barometer(
        state = BarometerScreen.State(
            pressure = PressureState.Loading,
        ),
    )
}
