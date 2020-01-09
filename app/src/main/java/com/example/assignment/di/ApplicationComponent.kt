package com.example.assignment.di

import com.example.assignment.exchange.di.ExchangeComponent
import com.example.assignment.exchange.view.ExchangeActivity
import com.example.assignment.exchangeSymbols.di.ExchangeSymbolComponent
import com.example.assignment.jsonplaceholder.di.PostComponent
import com.example.assignment.symbols.di.SymbolComponent
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(activity: ExchangeActivity)

    fun requestExchangeComponentBuilder(): ExchangeComponent.Builder

    fun requestExchangeSymbolComponentBuilder(): ExchangeSymbolComponent.Builder

    fun requestSymbolComponentBuilder(): SymbolComponent.Builder

    fun requestPostComponentBuilder(): PostComponent.Builder
}