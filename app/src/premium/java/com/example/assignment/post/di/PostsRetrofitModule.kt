package com.example.assignment.post.di

import com.example.assignment.api.PostsRetrofitService
import com.example.assignment.core.JSON_API_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object PostsRetrofitModule {

    @JvmStatic
    @Provides
    fun provideJsonRetrofitService(interceptor: Interceptor): PostsRetrofitService {
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()


        return Retrofit.Builder()
            .baseUrl(JSON_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PostsRetrofitService::class.java)
    }
}