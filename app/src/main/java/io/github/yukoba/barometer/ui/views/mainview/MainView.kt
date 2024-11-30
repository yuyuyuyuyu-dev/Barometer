package io.github.yukoba.barometer.ui.views.mainview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.yukoba.barometer.R

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (uiState.barometricPressure != null) {
                "${uiState.barometricPressure.toString().padEnd(10, '0')} hPa"
            } else {
                stringResource(R.string.could_not_get_barometric_sensor_value)
            },
        )
    }
}
