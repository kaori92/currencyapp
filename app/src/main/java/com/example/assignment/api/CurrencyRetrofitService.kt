package com.example.assignment.api

import com.example.assignment.BuildConfig
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyRetrofitService {

    @GET("latest")
    fun getExchangeRates(@Query("access_key") key: String = BuildConfig.API_KEY): Observable<ExchangeRates>

    @GET("symbols")
    fun getSymbols(@Query("access_key") key: String = BuildConfig.API_KEY): Observable<SymbolsMap>

    @GET("{date}")
    fun getRatesForDate(
        @Path("date") date: String,
        @Query("access_key") key: String = BuildConfig.API_KEY
    ): Maybe<ExchangeRates>

}