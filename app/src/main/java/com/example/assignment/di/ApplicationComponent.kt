package com.example.assignment.di

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.core.LogService
import com.example.assignment.core.MyApplication
import com.example.assignment.currency.di.CurrencyModule
import com.example.assignment.exchange.di.ExchangeComponent
import com.example.assignment.exchangeSymbols.di.ExchangeSymbolComponent
import com.example.assignment.interceptor.di.InterceptorModule
import com.example.assignment.post.di.PostComponent
import dagger.Component
import com.example.assignment.post.di.PostsRetrofitModule

@Component(
    modules = [
        AppModule::class,
        CurrencyModule::class,
        PostsRetrofitModule::class,
        LoggerModule::class,
        StringServiceModule::class,
        InterceptorModule::class]
)
interface ApplicationComponent {

    fun getLogger(): LogService

    fun getCurrencyRetrofitService(): CurrencyRetrofitService

    fun getApplication(): MyApplication

    fun requestExchangeComponentBuilder(): ExchangeComponent.Builder

    fun requestExchangeSymbolComponentBuilder(): ExchangeSymbolComponent.Builder

    fun requestPostComponentBuilder(): PostComponent.Builder
}