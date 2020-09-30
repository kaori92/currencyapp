package com.example.assignment.exchangeSymbols.di

import com.example.assignment.di.ExchangeApiScope
import com.example.assignment.exchangeSymbols.presenter.ExchangeSymbolPresenter
import dagger.Subcomponent

@ExchangeApiScope
@Subcomponent(modules = [ExchangeSymbolModule::class])
interface ExchangeSymbolComponent {

    fun presenter(): ExchangeSymbolPresenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): ExchangeSymbolComponent
    }
}