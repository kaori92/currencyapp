package com.example.assignment.symbols.di

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.core.AndroidSchedulerProvider
import com.example.assignment.core.LogService
import com.example.assignment.symbols.models.DefaultSymbolModel
import com.example.assignment.symbols.models.SymbolModel
import com.example.assignment.symbols.presenter.SymbolPresenter
import dagger.Module
import dagger.Provides

@Module
object SymbolModule {

    @JvmStatic
    @Provides
    fun provideModel(
        retrofitService: CurrencyRetrofitService
    ): SymbolModel =
        DefaultSymbolModel(retrofitService)

    @JvmStatic
    @Provides
    fun providePresenter(
        model: SymbolModel,
        logger: LogService
    ): SymbolPresenter =
        SymbolPresenter(model, AndroidSchedulerProvider, logger)
}