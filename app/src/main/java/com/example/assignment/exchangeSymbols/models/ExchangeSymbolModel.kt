package com.example.assignment.exchangeSymbols.models

import com.example.assignment.core.SchedulerProvider
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolView
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable
import io.reactivex.Single

interface ExchangeSymbolModel {
    fun downloadExchangeRates(): Observable<ExchangeRates>

    fun downloadSymbols(): Single<SymbolsMap>

    fun combineRatesWithSymbols(viewState: ExchangeSymbolView, schedulerProvider: SchedulerProvider)
}
