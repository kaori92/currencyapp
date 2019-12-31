package com.example.assignment.exchangeSymbols.view

import com.example.assignment.BaseView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ExchangeSymbolView: MvpView, BaseView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setUpRecyclerView(exchangeRatesModel: Array<String>)

    @StateStrategyType(AddToEndStrategy::class)
    fun showErrorToast(error: Throwable)

}