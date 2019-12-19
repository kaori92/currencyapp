package com.example.assignment.exchange.components

import com.example.assignment.exchange.di.ExchangeModule
import com.example.assignment.exchange.presenter.ExchangePresenter
import dagger.Subcomponent

@Subcomponent(modules = [ExchangeModule::class])
interface ExchangeComponent {
    fun presenter(): ExchangePresenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): ExchangeComponent
    }
}
