package com.example.assignment.currency.di

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.core.CURRENCY_API_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object CurrencyModule {

    @JvmStatic
    @Provides
    fun provideCurrencyRetrofitService(interceptor: Interceptor): CurrencyRetrofitService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(CURRENCY_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CurrencyRetrofitService::class.java)
    }
}