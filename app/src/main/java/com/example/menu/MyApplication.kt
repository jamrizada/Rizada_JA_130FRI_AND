package com.example.menu

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import timber.log.Timber

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
