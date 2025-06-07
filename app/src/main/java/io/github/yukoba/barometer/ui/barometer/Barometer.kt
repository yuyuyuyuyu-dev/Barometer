package io.github.yukoba.barometer.ui.barometer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import io.github.yukoba.barometer.R

@Composable
fun Barometer(state: BarometerScreen.State, modifier: Modifier = Modifier) = Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
) {
    Text(
        text = state.pressure ?: stringResource(R.string.could_not_get_barometric_sensor_value),
        fontSize = 34.sp,
    )
}
