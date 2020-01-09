package com.example.assignment.exchangeSymbols.presenter

import com.example.assignment.core.AndroidSchedulerProvider
import com.example.assignment.exchangeSymbols.models.ExchangeSymbolModel
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ExchangeSymbolPresenter(
    private val model: ExchangeSymbolModel
) : MvpPresenter<ExchangeSymbolView>() {

    fun getExchangeSymbols() {
        model.combineRatesWithSymbols(viewState, AndroidSchedulerProvider)
    }
}