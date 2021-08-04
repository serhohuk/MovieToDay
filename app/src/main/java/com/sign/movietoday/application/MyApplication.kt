package com.sign.movietoday.application

import android.app.Application
import com.sign.movietoday.di.AppComponent
import com.sign.movietoday.di.AppModule
import com.sign.movietoday.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}