package com.example.assignment.symbols.presenter

import com.example.assignment.core.BasePresenter
import com.example.assignment.core.LogService
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.exchange.view.ExchangeView
import com.example.assignment.symbols.models.SymbolModel
import com.example.assignment.symbols.view.SymbolView
import moxy.InjectViewState

@InjectViewState
class SymbolPresenter(
    private val model: SymbolModel,
    private val schedulerProvider: SchedulerProvider,
    private val logger: LogService
) : BasePresenter<SymbolView>() {

    fun getSymbols() {
        val disposable = model.downloadSymbols()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setSymbols(it)
            }, {
                viewState.showError(it)
                logger.log(ExchangeView::class.java.name, "Failure getting symbols ${it?.message}")
            })

        compositeDisposable.add(disposable)
    }

}