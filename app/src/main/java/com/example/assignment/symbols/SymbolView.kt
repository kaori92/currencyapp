package com.example.assignment.symbols

import com.example.assignment.BaseView
import com.example.assignment.symbols.data.SymbolsMap
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SymbolView: MvpView, BaseView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setSymbols(symbolsMap: SymbolsMap)

    @StateStrategyType(AddToEndStrategy::class)
    fun showErrorToast(error: Throwable)
}