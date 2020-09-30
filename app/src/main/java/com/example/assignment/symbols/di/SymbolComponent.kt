package com.example.assignment.symbols.di

import com.example.assignment.di.ApplicationComponent
import com.example.assignment.di.ExchangeApiScope
import com.example.assignment.symbols.presenter.SymbolPresenter
import dagger.Component

@ExchangeApiScope
@Component(
    dependencies = [
        ApplicationComponent::class
    ],
    modules = [SymbolModule::class])
interface SymbolComponent {
    fun presenter(): SymbolPresenter
}