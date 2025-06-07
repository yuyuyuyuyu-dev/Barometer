package io.github.yukoba.barometer.di

import com.slack.circuit.foundation.Circuit
import io.github.yukoba.barometer.ui.barometer.Barometer
import io.github.yukoba.barometer.ui.barometer.BarometerPresenter
import io.github.yukoba.barometer.ui.barometer.BarometerScreen
import org.koin.dsl.module

val uiModule = module {
    single {
        Circuit.Builder()
            .addUi<BarometerScreen, BarometerScreen.State> { _, modifier -> Barometer(modifier) }
            .addPresenter<BarometerScreen, BarometerScreen.State>(
                BarometerPresenter(getFormattedBarometricPressureFlowUseCase = get()),
            )

            .build()
    }
}
