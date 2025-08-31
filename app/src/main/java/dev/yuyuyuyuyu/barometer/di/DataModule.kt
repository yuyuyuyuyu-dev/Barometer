package dev.yuyuyuyuyu.barometer.di

import android.content.Context
import android.hardware.SensorManager
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import dev.yuyuyuyuyu.barometer.data.repository.impl.BarometricPressureRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    singleOf(::BarometricPressureRepositoryImpl) { bind<BarometricPressureRepository>() }
}
