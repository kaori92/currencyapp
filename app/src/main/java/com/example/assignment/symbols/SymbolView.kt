package com.example.assignment.symbols

import com.example.assignment.BaseView
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.Symbols
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SymbolView: MvpView, BaseView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setSymbols(symbols: Symbols)

    @StateStrategyType(AddToEndStrategy::class)
    fun showErrorToast(error: Throwable)
}