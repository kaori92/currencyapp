package com.example.assignment

import android.app.Application
import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.exchange.components.ExchangeComponent
import com.example.assignment.exchangeSymbols.components.ExchangeSymbolComponent
import com.example.assignment.retrofit.NetworkModule
import com.example.assignment.symbols.components.SymbolComponent
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(activity: ExchangeActivity)

    fun requestExchangeComponentBuilder(): ExchangeComponent.Builder

    fun requestExchangeSymbolComponentBuilder(): ExchangeSymbolComponent.Builder

    fun requestSymbolComponentBuilder(): SymbolComponent.Builder
}

class MyApplicationComponent: Application() {
    val appComponent =
        DaggerApplicationComponent.create()
}