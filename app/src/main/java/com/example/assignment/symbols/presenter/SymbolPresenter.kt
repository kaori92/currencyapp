package com.example.assignment.symbols.presenter

import com.example.assignment.core.SchedulerProvider
import com.example.assignment.exchange.view.ExchangeActivity
import com.example.assignment.symbols.models.SymbolModel
import com.example.assignment.symbols.view.SymbolView
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.logging.Logger

@InjectViewState
class SymbolPresenter(
    private val model: SymbolModel,
    private val schedulerProvider: SchedulerProvider
) : MvpPresenter<SymbolView>() {

    fun getSymbols() {
        model.downloadSymbols()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setSymbols(it)
            }, {
                viewState.showError(it)
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("failure ${it?.message}")
            })
    }

}