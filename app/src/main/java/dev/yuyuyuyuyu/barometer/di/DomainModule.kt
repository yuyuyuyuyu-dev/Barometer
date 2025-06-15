package dev.yuyuyuyuyu.barometer.di

import dev.yuyuyuyuyu.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import dev.yuyuyuyuyu.barometer.domain.useCases.GetFormattedBarometricPressureFlowUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetFormattedBarometricPressureFlowUseCaseImpl) { bind<GetFormattedBarometricPressureFlowUseCase>() }
}
