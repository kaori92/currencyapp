package com.example.assignment.core

import androidx.multidex.MultiDexApplication
import com.example.assignment.di.AppModule
import com.example.assignment.di.ApplicationComponent
import com.example.assignment.di.DaggerApplicationComponent

class MyApplication : MultiDexApplication() {

    lateinit  var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}