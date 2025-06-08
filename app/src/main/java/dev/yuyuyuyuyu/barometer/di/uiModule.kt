package dev.yuyuyuyuyu.barometer.di

import com.slack.circuit.foundation.Circuit
import dev.yuyuyuyuyu.barometer.ui.barometer.Barometer
import dev.yuyuyuyuyu.barometer.ui.barometer.BarometerPresenter
import dev.yuyuyuyuyu.barometer.ui.barometer.BarometerScreen
import dev.yuyuyuyuyu.barometer.ui.openSourceLicensesList.OpenSourceLicenseList
import dev.yuyuyuyuyu.barometer.ui.openSourceLicensesList.OpenSourceLicenseListPresenter
import dev.yuyuyuyuyu.barometer.ui.openSourceLicensesList.OpenSourceLicenseListScreen
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
