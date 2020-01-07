package com.example.assignment.symbols.di

import com.example.assignment.api.CurrencyRetrofitService
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
        model: SymbolModel
    ): SymbolPresenter =
        SymbolPresenter(model)
}