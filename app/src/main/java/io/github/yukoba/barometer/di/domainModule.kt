package io.github.yukoba.barometer.di

import io.github.yukoba.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import io.github.yukoba.barometer.domain.useCases.GetFormattedBarometricPressureFlowUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetFormattedBarometricPressureFlowUseCaseImpl) { bind<GetFormattedBarometricPressureFlowUseCase>() }
}
