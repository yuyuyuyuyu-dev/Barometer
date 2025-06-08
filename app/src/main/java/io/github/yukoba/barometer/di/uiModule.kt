package io.github.yukoba.barometer.di

import com.slack.circuit.foundation.Circuit
import io.github.yukoba.barometer.ui.barometer.Barometer
import io.github.yukoba.barometer.ui.barometer.BarometerPresenter
import io.github.yukoba.barometer.ui.barometer.BarometerScreen
import io.github.yukoba.barometer.ui.openSourceLicensesList.OpenSourceLicenseList
import io.github.yukoba.barometer.ui.openSourceLicensesList.OpenSourceLicenseListPresenter
import io.github.yukoba.barometer.ui.openSourceLicensesList.OpenSourceLicenseListScreen
import org.koin.dsl.module

val uiModule = module {
    single {
        Circuit.Builder()
            .addUi<BarometerScreen, BarometerScreen.State> { state, modifier ->
                Barometer(state, modifier)
            }
            .addPresenter<BarometerScreen, BarometerScreen.State>(
                BarometerPresenter(getFormattedBarometricPressureFlowUseCase = get()),
            )

            .addUi<OpenSourceLicenseListScreen, OpenSourceLicenseListScreen.State> { _, modifier ->
                OpenSourceLicenseList(modifier)
            }
            .addPresenter<OpenSourceLicenseListScreen, OpenSourceLicenseListScreen.State>(
                OpenSourceLicenseListPresenter(),
            )

            .build()
    }
}
