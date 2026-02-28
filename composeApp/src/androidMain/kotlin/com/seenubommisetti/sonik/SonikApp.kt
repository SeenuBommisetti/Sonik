package com.seenubommisetti.sonik

import android.app.Application
import com.seenubommisetti.sonik.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class SonikApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        initKoin {
            androidLogger()
            androidContext(this@SonikApp)
        }
    }
}
