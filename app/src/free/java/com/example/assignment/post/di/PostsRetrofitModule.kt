package com.example.assignment.post.di

import com.example.assignment.api.PostsRetrofitService
import com.example.assignment.post.MockPostsRetrofitService
import dagger.Module
import dagger.Provides

@Module
object PostsRetrofitModule {

    @JvmStatic
    @Provides
    fun provideJsonRetrofitService(): PostsRetrofitService = MockPostsRetrofitService()
}