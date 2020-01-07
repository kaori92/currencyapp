package com.example.assignment.symbols.view

import com.example.assignment.core.BaseView
import com.example.assignment.symbols.data.SymbolsMap
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SymbolView : BaseView {

    @StateStrategyType(AddToEndStrategy::class)
    fun setSymbols(symbolsMap: SymbolsMap)

}