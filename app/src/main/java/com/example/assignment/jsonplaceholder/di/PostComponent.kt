package com.example.assignment.jsonplaceholder.di

import com.example.assignment.jsonplaceholder.presenter.PostPresenter
import dagger.Subcomponent

@Subcomponent(modules = [PostModule::class])
interface PostComponent {
    fun postPresenter(): PostPresenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): PostComponent
    }
}
