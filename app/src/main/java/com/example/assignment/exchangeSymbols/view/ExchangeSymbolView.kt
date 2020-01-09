package com.example.assignment.exchangeSymbols.view

import com.example.assignment.core.BaseView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ExchangeSymbolView : BaseView {

    @StateStrategyType(AddToEndStrategy::class)
    fun setUpRecyclerView(exchangeRatesModel: Array<String>)

}