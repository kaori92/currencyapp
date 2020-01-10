package com.example.assignment.posts.di

import com.example.assignment.posts.presenter.PostPresenter
import dagger.Subcomponent

@Subcomponent(modules = [PostModule::class])
interface PostComponent {
    fun postPresenter(): PostPresenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): PostComponent
    }
}
