package com.example.assignment.exchangeSymbols.models

import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable
import io.reactivex.Single

interface ExchangeSymbolModel {
    fun downloadExchangeSymbols(): Observable<Array<String>>

    fun combineSymbolWithRate(symbols: SymbolsMap, rates: ExchangeRates): Array<String>
}
