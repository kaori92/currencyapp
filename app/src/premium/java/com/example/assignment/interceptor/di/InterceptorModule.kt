package com.example.assignment.interceptor.di

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
object InterceptorModule {

    @JvmStatic
    @Provides
    fun provideInterceptor(): Interceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

}