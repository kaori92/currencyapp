package com.example.assignment.di

import com.example.assignment.core.ExchangeLogService
import com.example.assignment.core.LogService
import dagger.Module
import dagger.Provides

@Module
object LoggerModule {

    @JvmStatic
    @Provides
    fun provideLogger(): LogService = ExchangeLogService()
}