package com.example.assignment.core

import android.app.Application
import com.example.assignment.di.ApplicationComponent
import com.example.assignment.di.DaggerApplicationComponent

class MyApplication : Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

}