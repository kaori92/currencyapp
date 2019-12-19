package com.example.assignment.exchange.models

import com.example.assignment.BuildConfig
import com.example.assignment.exchange.data.data.ExchangeRates
import com.example.assignment.retrofit.CurrencyRetrofitService
import io.reactivex.Single
import javax.inject.Inject

data class ExchangeRatesModel(
    private val apiService: CurrencyRetrofitService
) {
    fun downloadExchangeRates(): Single<ExchangeRates> {
        return apiService.getExchangeRates(BuildConfig.API_KEY)
    }
}