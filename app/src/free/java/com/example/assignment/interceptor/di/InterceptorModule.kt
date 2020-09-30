package com.example.assignment.interceptor.di

import com.example.assignment.interceptor.MockInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

@Module
object InterceptorModule {

    @JvmStatic
    @Provides
    fun provideInterceptor(): Interceptor = MockInterceptor()
}