package com.example.assignment.retrofit

import com.example.assignment.models.ExchangeRates
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("latest")
    fun getExchangeRates(@Query("access_key") key: String): Observable<ExchangeRates>
}
