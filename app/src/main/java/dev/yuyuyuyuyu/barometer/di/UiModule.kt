package dev.yuyuyuyuyu.barometer.di

import dev.yuyuyuyuyu.barometer.ui.barometer.BarometerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule =
    module {
        viewModelOf(::BarometerViewModel)
    }
