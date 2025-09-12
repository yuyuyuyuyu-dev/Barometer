package dev.yuyuyuyuyu.barometer.di

import dev.yuyuyuyuyu.barometer.di.model.ScopeQualifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val barometerAppModule = module {
    single(named(ScopeQualifier.IO)) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    includes(uiModule, domainModule, dataModule)
}
