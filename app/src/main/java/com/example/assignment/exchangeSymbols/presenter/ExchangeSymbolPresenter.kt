package com.example.assignment.exchangeSymbols.presenter

import com.example.assignment.core.BasePresenter
import com.example.assignment.core.LogService
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.exchangeSymbols.models.ExchangeSymbolModel
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolView
import com.example.assignment.post.presenter.PostPresenter
import moxy.InjectViewState

@InjectViewState
class ExchangeSymbolPresenter(
    private val model: ExchangeSymbolModel,
    private val schedulerProvider: SchedulerProvider,
    private val logger: LogService
) : BasePresenter<ExchangeSymbolView>() {

    fun getExchangeSymbols() {
        val disposable = model.downloadExchangeSymbols()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showError(it)
                logger.log(
                    PostPresenter::class.java.name,
                    "Failure getting exchange symbols ${it?.message}"
                )
            })

        compositeDisposable.add(disposable)
    }


}