package com.example.assignment

import android.app.Application
import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.exchange.components.ExchangeComponent
import com.example.assignment.retrofit.NetworkModule
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(activity: ExchangeActivity)

    fun requestExchangeComponentBuilder(): ExchangeComponent.Builder
}

class MyApplicationComponent: Application() {
    val appComponent =
        DaggerApplicationComponent.create()
}