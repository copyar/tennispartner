package com.example.android.tennispartner

import android.app.Application
import timber.log.Timber

class TennisPartnerApp: Application() {
    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}