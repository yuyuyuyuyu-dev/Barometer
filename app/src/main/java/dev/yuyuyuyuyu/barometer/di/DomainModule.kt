package dev.yuyuyuyuyu.barometer.di

import dev.yuyuyuyuyu.barometer.domain.useCase.FormatPressureUseCase
import dev.yuyuyuyuyu.barometer.domain.useCase.impl.FormatPressureUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::FormatPressureUseCaseImpl) { bind<FormatPressureUseCase>() }
}
