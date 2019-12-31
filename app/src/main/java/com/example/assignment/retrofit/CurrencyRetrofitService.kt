package com.example.assignment.retrofit

import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyRetrofitService {
    @GET("latest")
    fun getExchangeRates(@Query("access_key") key: String): Observable<ExchangeRates>

    @GET("symbols")
    fun getSymbols(@Query("access_key") key: String): Observable<SymbolsMap>

    @GET("{date}")
    fun getRatesForDate(
        @Path("date") date: String,
        @Query("access_key") key: String): Maybe<ExchangeRates>
}