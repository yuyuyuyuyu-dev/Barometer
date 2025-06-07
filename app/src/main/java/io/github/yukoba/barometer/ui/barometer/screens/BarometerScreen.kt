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
import io.github.yukoba.barometer.ui.barometer.types.BarometerUiState

@Composable
fun BarometerScreen(
    barometerUiState: BarometerUiState,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = when (barometerUiState) {
                is BarometerUiState.Success -> barometerUiState.pressure
                is BarometerUiState.Error -> stringResource(R.string.could_not_get_barometric_sensor_value)
            },
            fontSize = 34.sp,
        )
    }
}

@Preview
@Composable
private fun BarometerScreenPreview() {
    BarometerScreen(BarometerUiState.Success("149.73100 hPa"))
}

@Preview
@Composable
private fun BarometerScreenErrorPreview() {
    BarometerScreen(BarometerUiState.Error)
}
