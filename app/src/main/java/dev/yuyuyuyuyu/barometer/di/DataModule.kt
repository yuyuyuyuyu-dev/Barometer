package dev.yuyuyuyuyu.barometer.di

import android.content.Context
import android.hardware.SensorManager
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import dev.yuyuyuyuyu.barometer.data.repository.impl.BarometricPressureRepositoryImpl
import dev.yuyuyuyuyu.barometer.di.model.ScopeQualifier
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single { androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    single<BarometricPressureRepository> {
        BarometricPressureRepositoryImpl(
            sensorManager = get(),
            ioScope = get(named(ScopeQualifier.IO)),
        )
    }
}
