package dev.yuyuyuyuyu.barometer

import android.app.Application
import dev.yuyuyuyuyu.barometer.di.barometerAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BarometerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BarometerApplication)
            modules(barometerAppModule)
        }
    }
}
