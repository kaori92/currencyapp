package com.example.assignment.symbols.di

import com.example.assignment.retrofit.CurrencyRetrofitService
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
        SymbolModel(retrofitService)

    @JvmStatic
    @Provides
    fun providePresenter(
        model: SymbolModel
    ): SymbolPresenter =
        SymbolPresenter(model)
}