package com.example.assignment.exchangeSymbols.models

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables

class DefaultExchangeSymbolModel(
    private val apiService: CurrencyRetrofitService
) : ExchangeSymbolModel {

    private fun downloadExchangeRates(): Observable<ExchangeRates> {
        return apiService.getExchangeRates()
    }

    private fun downloadSymbols(): Single<SymbolsMap> {
        return apiService.getSymbols()
    }

    override fun downloadExchangeSymbols(): Observable<Array<String>>{
        return Observables.zip(
            downloadSymbols().toObservable(),
            downloadExchangeRates()
        )
        { symbolsMap, rates ->
            combineSymbolWithRate(symbolsMap, rates)
        }
    }

    override fun combineSymbolWithRate(symbols: SymbolsMap, rates: ExchangeRates): Array<String> {
        val list = mutableListOf<String>()
        symbols.map.forEach { (base, name) ->
            val rate = rates.rates[base]
            list.add("$base $name $rate")
        }

        return list.toTypedArray()
    }
}
