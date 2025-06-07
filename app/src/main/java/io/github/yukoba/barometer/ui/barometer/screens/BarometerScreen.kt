package io.github.yukoba.barometer.ui.barometer.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.github.yukoba.barometer.R

@Composable
fun BarometerScreen(
    barometricPressure: Float?,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (barometricPressure != null) {
                "${
                    barometricPressure.toString()
                        .padEnd(
                            if (barometricPressure < 1000) 9 else 10,
                            '0',
                        )
                } hPa"
            } else {
                stringResource(R.string.could_not_get_barometric_sensor_value)
            },
            fontSize = 34.sp,
        )
    }
}

@Preview
@Composable
private fun BarometerScreenPreview() {
    BarometerScreen(barometricPressure = 149.731f)
}
