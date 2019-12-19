package com.example.assignment.exchange.components

import android.app.Application
import com.example.assignment.exchange.di.ExchangeModule
import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.exchange.di.NetworkModule
import dagger.Component
import javax.inject.Singleton


@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(activity: ExchangeActivity)

    fun requestExchangeComponentBuilder(): ExchangeComponent.Builder
}

class MyApplicationComponent: Application() {
    val appComponent = DaggerApplicationComponent.create()
}