package com.example.assignment.exchangeSymbols.di

import com.example.assignment.exchangeSymbols.models.ExchangeSymbolModel
import com.example.assignment.exchangeSymbols.presenter.ExchangeSymbolPresenter
import com.example.assignment.retrofit.CurrencyRetrofitService
import dagger.Module
import dagger.Provides

@Module
object ExchangeSymbolModule {

    @JvmStatic
    @Provides
    fun provideModel(
        retrofitService: CurrencyRetrofitService
    ): ExchangeSymbolModel =
        ExchangeSymbolModel(retrofitService)

    @JvmStatic
    @Provides
    fun providePresenter(
        model: ExchangeSymbolModel
    ): ExchangeSymbolPresenter =
        ExchangeSymbolPresenter(model)
}