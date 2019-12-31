package com.example.assignment.exchange.models

import com.example.assignment.BuildConfig
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.retrofit.CurrencyRetrofitService
import io.reactivex.Maybe
import io.reactivex.Observable

data class ExchangeRatesModel(
    private val apiService: CurrencyRetrofitService
) {
    fun downloadExchangeRates(): Observable<ExchangeRates> {
        return apiService.getExchangeRates(BuildConfig.API_KEY)
    }

    fun downloadRatesForDate(date: String): Maybe<ExchangeRates> {
        return apiService.getRatesForDate(date, BuildConfig.API_KEY)
    }
}