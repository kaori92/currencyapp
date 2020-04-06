package com.example.assignment.di

import com.example.assignment.core.MyApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val mApplication: MyApplication) {

    @Provides
    internal fun provideApplication(): MyApplication {
        return mApplication
    }

}
