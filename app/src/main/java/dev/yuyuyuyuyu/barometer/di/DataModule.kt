package dev.yuyuyuyuyu.barometer.di

import android.content.Context
import android.hardware.SensorManager
import dev.yuyuyuyuyu.barometer.data.dataSource.BarometricPressureDataSource
import dev.yuyuyuyuyu.barometer.data.dataSource.impl.BarometricPressureDataSourceImpl
import dev.yuyuyuyuyu.barometer.data.repository.BarometricPressureRepository
import dev.yuyuyuyuyu.barometer.data.repository.impl.BarometricPressureRepositoryImpl
import dev.yuyuyuyuyu.barometer.di.model.ScopeQualifier
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<BarometricPressureRepository> {
        BarometricPressureRepositoryImpl(
            barometricPressureDataSource = get(),
            ioScope = get(named(ScopeQualifier.IO)),
        )
    }

    singleOf(::BarometricPressureDataSourceImpl) { bind<BarometricPressureDataSource>() }

    single { androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager }
}
