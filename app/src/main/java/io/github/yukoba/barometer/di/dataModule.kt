package io.github.yukoba.barometer.di

import android.content.Context
import android.hardware.SensorManager
import io.github.yukoba.barometer.data.models.BarometricPressureRepository
import io.github.yukoba.barometer.data.repositories.BarometricPressureRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    singleOf(::BarometricPressureRepositoryImpl) { bind<BarometricPressureRepository>() }
}
