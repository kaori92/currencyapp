package com.example.assignment.exchange.di

import com.example.assignment.di.ExchangeApiScope
import com.example.assignment.exchange.presenter.ExchangePresenter
import dagger.Subcomponent

@ExchangeApiScope
@Subcomponent(modules = [ExchangeModule::class])
interface ExchangeComponent {
    fun presenter(): ExchangePresenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): ExchangeComponent
    }
}
