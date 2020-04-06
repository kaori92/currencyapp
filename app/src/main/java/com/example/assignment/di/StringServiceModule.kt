package com.example.assignment.di

import android.content.Context
import com.example.assignment.core.DefaultStringService
import com.example.assignment.core.MyApplication
import com.example.assignment.core.StringService
import dagger.Module
import dagger.Provides

@Module
object StringServiceModule {
        @JvmStatic
        @Provides
        fun provideStringService(context: MyApplication): StringService =
            DefaultStringService(context)

}