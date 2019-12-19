package com.example.assignment.exchange.components

import android.app.Application
import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.exchange.di.ExchangeModule
import com.example.assignment.exchange.presenter.ExchangePresenter
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(modules = [ExchangeModule::class])
interface ExchangeComponent {
    fun presenter(): ExchangePresenter
    fun inject(activity: ExchangeActivity) // ??

    @Subcomponent.Builder
    interface Builder {
        // jakas adnotacja?
        fun build(): ExchangeComponent
    }
}
