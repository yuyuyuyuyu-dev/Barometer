package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import dev.yuyuyuyuyu.barometer.R
import dev.yuyuyuyuyu.barometer.data.errors.BarometricPressureRepositoryError
import dev.yuyuyuyuyu.barometer.domain.errors.DomainError

@Composable
fun Barometer(state: BarometerScreen.State, modifier: Modifier = Modifier) = Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
) {
    Text(
        text = if (state.pressure.isErr) {
            when (val error = state.pressure.error) {
                is DomainError.FromDataLayer -> {
                    when (error.error) {
                        is BarometricPressureRepositoryError.DeviceDoesNotHaveBarometricSensor -> {
                            stringResource(R.string.could_not_get_barometric_sensor_value)
                        }
                    }
                }
            }
        } else {
            state.pressure.value ?: stringResource(R.string.loading)
        },
        fontSize = 34.sp,
    )
}
