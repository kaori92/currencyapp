package com.example.assignment.exchange.models

import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.retrofit.CurrencyRetrofitService
import io.reactivex.Maybe
import io.reactivex.Observable

class DefaultExchangeRatesModel(
    private val apiService: CurrencyRetrofitService
): ExchangeRatesModel {

    override fun downloadExchangeRates(): Observable<ExchangeRates> {
        return apiService.getExchangeRates()
    }

    override fun downloadRatesForDate(date: String): Maybe<ExchangeRates> {
        return apiService.getRatesForDate(date)
    }
}