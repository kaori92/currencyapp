package com.example.assignment.exchangeSymbols.di

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.core.AndroidSchedulerProvider
import com.example.assignment.core.ExchangeLogService
import com.example.assignment.exchangeSymbols.models.DefaultExchangeSymbolModel
import com.example.assignment.exchangeSymbols.models.ExchangeSymbolModel
import com.example.assignment.exchangeSymbols.presenter.ExchangeSymbolPresenter
import dagger.Module
import dagger.Provides

@Module
object ExchangeSymbolModule {

    @JvmStatic
    @Provides
    fun provideModel(
        retrofitService: CurrencyRetrofitService
    ): ExchangeSymbolModel =
        DefaultExchangeSymbolModel(retrofitService)

    @JvmStatic
    @Provides
    fun providePresenter(
        model: ExchangeSymbolModel,
        logger: ExchangeLogService
    ): ExchangeSymbolPresenter =
        ExchangeSymbolPresenter(model, AndroidSchedulerProvider, logger)
}