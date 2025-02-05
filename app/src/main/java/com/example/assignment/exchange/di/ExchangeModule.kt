package com.example.assignment.exchange.di

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.core.AndroidSchedulerProvider
import com.example.assignment.core.LogService
import com.example.assignment.core.StringService
import com.example.assignment.exchange.models.DefaultExchangeRatesModel
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.example.assignment.exchange.presenter.ExchangePresenter
import dagger.Module
import dagger.Provides

@Module
object ExchangeModule {

    @JvmStatic
    @Provides
    fun provideModel(
        retrofitService: CurrencyRetrofitService
    ): ExchangeRatesModel =
        DefaultExchangeRatesModel(retrofitService)

    @JvmStatic
    @Provides
    fun providePresenter(
        model: ExchangeRatesModel,
        logger: LogService,
        stringService: StringService
    ): ExchangePresenter =
        ExchangePresenter(model, AndroidSchedulerProvider, logger, stringService)
}