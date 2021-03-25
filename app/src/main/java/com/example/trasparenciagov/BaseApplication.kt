package com.example.trasparenciagov

import android.app.Application
import com.example.trasparenciagov.di.infocanModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(infocanModule)

        }
    }
}