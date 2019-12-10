package com.example.assignment

import com.example.assignment.models.ExchangeRate
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("exchangeRates")
    fun getPhotos(): Call<List<ExchangeRate>>

}
