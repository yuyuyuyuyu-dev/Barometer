package dev.yuyuyuyuyu.barometer.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val barometerAppModule = module {
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    includes(uiModule, domainModule, dataModule)
}
