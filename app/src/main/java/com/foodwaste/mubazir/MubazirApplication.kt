package com.foodwaste.mubazir

import android.app.Application
import timber.log.Timber

class MubazirApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}