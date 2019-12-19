package com.example.assignment.exchange.view

import com.example.assignment.exchange.data.ExchangeRates
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ExchangeView: MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setUpRecyclerView(exchangeRatesModel: ExchangeRates)
}