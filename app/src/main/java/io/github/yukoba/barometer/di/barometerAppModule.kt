package io.github.yukoba.barometer.di

import org.koin.dsl.module

val barometerAppModule = module {
    includes(uiModule, domainModule, dataModule)
}
