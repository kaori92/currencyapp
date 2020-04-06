package com.example.assignment.di

import android.content.Context
import com.example.assignment.core.MyApplication
import com.example.assignment.exchange.di.ExchangeComponent
import com.example.assignment.exchange.view.ExchangeActivity
import com.example.assignment.exchangeSymbols.di.ExchangeSymbolComponent
import com.example.assignment.posts.di.PostComponent
import com.example.assignment.symbols.di.SymbolComponent
import dagger.Component

@Component(modules = [AppModule::class, NetworkModule::class, LoggerModule::class, StringServiceModule::class])
interface ApplicationComponent {

    fun inject(activity: ExchangeActivity)

    fun getApplication(): MyApplication

    fun requestExchangeComponentBuilder(): ExchangeComponent.Builder

    fun requestExchangeSymbolComponentBuilder(): ExchangeSymbolComponent.Builder

    fun requestSymbolComponentBuilder(): SymbolComponent.Builder

    fun requestPostComponentBuilder(): PostComponent.Builder
}