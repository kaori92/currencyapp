package com.example.assignment.exchangeSymbols.models

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable

class DefaultExchangeSymbolModel(
    private val apiService: CurrencyRetrofitService
) : ExchangeSymbolModel {

    override fun downloadExchangeRates(): Observable<ExchangeRates> {
        return apiService.getExchangeRates()
    }

    override fun downloadSymbols(): Observable<SymbolsMap> {
        return apiService.getSymbols()
    }

}
