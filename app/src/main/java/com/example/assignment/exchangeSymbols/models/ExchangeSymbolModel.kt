package com.example.assignment.exchangeSymbols.models

import com.example.assignment.BuildConfig
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.retrofit.CurrencyRetrofitService
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable

class ExchangeSymbolModel(private val apiService: CurrencyRetrofitService){
    //TODO remove duplicate from ExchangeRatesModel.class
    fun downloadExchangeRates(): Observable<ExchangeRates> {
        return apiService.getExchangeRates(BuildConfig.API_KEY)
    }

    //TODO remove duplicate from SymbolModel.class
    fun downloadSymbols(): Observable<SymbolsMap> {
        return apiService.getSymbols(BuildConfig.API_KEY)
    }
}