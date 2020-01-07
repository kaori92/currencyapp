package com.example.assignment.symbols.presenter

import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.symbols.SymbolView
import com.example.assignment.symbols.data.SymbolsMap
import com.example.assignment.symbols.models.SymbolModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.logging.Logger

@InjectViewState
class SymbolPresenter(
    private val model: SymbolModel
) : MvpPresenter<SymbolView>() {

    fun getSymbols(): Observable<SymbolsMap> {
        val symbolObservable = model.downloadSymbols()
            .observeOn(Schedulers.trampoline()) // tests
            .subscribeOn(Schedulers.trampoline()) // tests
//            .observeOn(AndroidSchedulers.mainThread()) // run app
//            .subscribeOn(Schedulers.io()) // run app

        symbolObservable
            .subscribe({
                viewState.setSymbols(it)
            }, {
                viewState.showError(it)
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("failure ${it?.message}")
            })

        return symbolObservable
    }

    fun getSymbolsFlowable(): Flowable<SymbolsMap> {
        val symbolObservable = model.downloadSymbols()
            .toFlowable(BackpressureStrategy.BUFFER)

        symbolObservable
            .observeOn(Schedulers.trampoline()) // tests
            .subscribeOn(Schedulers.trampoline()) // tests
//            .observeOn(AndroidSchedulers.mainThread()) // run app
//            .subscribeOn(Schedulers.io()) // run app
            .subscribe({
                viewState.setSymbols(it)
            }, {
                viewState.showError(it)
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("failure ${it?.message}")
            })

        return symbolObservable
    }

}