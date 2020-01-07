package com.example.assignment.symbols.di

import com.example.assignment.symbols.presenter.SymbolPresenter
import dagger.Subcomponent

@Subcomponent(modules = [SymbolModule::class])
interface SymbolComponent {
    fun presenter(): SymbolPresenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): SymbolComponent
    }
}