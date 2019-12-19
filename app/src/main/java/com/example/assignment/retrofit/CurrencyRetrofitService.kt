package com.example.assignment.retrofit

import com.example.assignment.exchange.data.ExchangeRates
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRetrofitService {
    @GET("latest")
    fun getExchangeRates(@Query("access_key") key: String): Single<ExchangeRates>
}