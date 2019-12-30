package com.example.assignment.symbols.presenter

import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.symbols.SymbolView
import com.example.assignment.symbols.models.SymbolModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

@InjectViewState
class SymbolPresenter(
    private val model: SymbolModel
): MvpPresenter<SymbolView>() {

    fun getSymbols(){
        model.downloadSymbols()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.setSymbols(it)
            }, {
                viewState.showErrorToast(it)
                Logger.getLogger(ExchangeActivity::class.java.name).warning("failure ${it?.message}")
            })
    }

    fun getSymbolsFlowable(){
        model.downloadSymbols()
            .toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.setSymbols(it)
            }, {
                viewState.showErrorToast(it)
                Logger.getLogger(ExchangeActivity::class.java.name).warning("failure ${it?.message}")
            })
    }

}