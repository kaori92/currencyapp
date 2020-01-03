package com.example.assignment.exchangeSymbols.models

import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable

interface ExchangeSymbolModel {
    fun downloadExchangeRates(): Observable<ExchangeRates>

    fun downloadSymbols(): Observable<SymbolsMap>
}
