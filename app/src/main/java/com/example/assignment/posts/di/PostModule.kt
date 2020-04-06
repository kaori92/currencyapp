package com.example.assignment.posts.di

import com.example.assignment.api.PostsRetrofitService
import com.example.assignment.core.AndroidSchedulerProvider
import com.example.assignment.core.LogService
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.core.StringService
import com.example.assignment.posts.models.DefaultPostModel
import com.example.assignment.posts.models.PostModel
import com.example.assignment.posts.presenter.PostPresenter
import dagger.Module
import dagger.Provides

@Module
object PostModule {

    @JvmStatic
    @Provides
    fun provideModel(
        postService: PostsRetrofitService
    ): PostModel =
        DefaultPostModel(postService)

    @JvmStatic
    @Provides
    fun providePresenter(
        model: PostModel,
        logger: LogService,
        stringService: StringService
    ): PostPresenter =
        PostPresenter(model, AndroidSchedulerProvider, logger, stringService)
}