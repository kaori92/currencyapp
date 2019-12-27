package com.example.assignment.retrofit

import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.Symbols
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRetrofitService {
    @GET("latest")
    fun getExchangeRates(@Query("access_key") key: String): Single<ExchangeRates>

    @GET("symbols")
    fun getSymbols(@Query("access_key") key: String): Observable<Symbols>
}