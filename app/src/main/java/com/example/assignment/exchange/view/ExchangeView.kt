package com.example.assignment.exchange.view

import com.example.assignment.core.BaseView
import com.example.assignment.exchange.data.ExchangeRates
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ExchangeView: BaseView {

    @StateStrategyType(AddToEndStrategy::class)
    fun setUpRecyclerView(exchangeRatesModel: ExchangeRates)

}