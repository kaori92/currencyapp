package com.example.assignment.post.di

import com.example.assignment.post.presenter.PostPresenter
import dagger.Subcomponent

@Subcomponent(modules = [PostModule::class])
interface PostComponent {
    fun postPresenter(): PostPresenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): PostComponent
    }
}
