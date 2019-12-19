package com.example.assignment.exchange.di

import com.example.assignment.exchange.components.ExchangeComponent
import com.example.assignment.exchange.presenter.ExchangePresenter
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.example.assignment.retrofit.CurrencyRetrofitService
import dagger.Module
import dagger.Provides

@Module
object ExchangeModule {
    @JvmStatic
    @Provides
    fun provideModel(
        retrofitService: CurrencyRetrofitService
    ): ExchangeRatesModel =
        ExchangeRatesModel(retrofitService)

    @JvmStatic
    @Provides
    fun providePresenter(
        model: ExchangeRatesModel
    ): ExchangePresenter =
        ExchangePresenter(model)
}