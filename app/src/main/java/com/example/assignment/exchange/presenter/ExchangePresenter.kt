package com.example.assignment.exchange.presenter

import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.example.assignment.exchange.view.ExchangeView
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

@InjectViewState
class ExchangePresenter(
    private val model: ExchangeRatesModel
) : MvpPresenter<ExchangeView>() {

    fun getExchangeRates(): Observable<ExchangeRates> {
        val observable = model.downloadExchangeRates()
            .observeOn(Schedulers.trampoline()) // tests
//             .observeOn(AndroidSchedulers.mainThread()) // run app
//            .subscribeOn(Schedulers.io()) // run app
            .subscribeOn(Schedulers.trampoline()) // tests

        observable
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showError(it)
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("Failure getting rates ${it?.message}")
            })

        return observable
    }

    fun getExchangeRatesPeriodically(): Disposable {
        return Observable.interval(5, TimeUnit.SECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("Getting exchange rates")
                getExchangeRates()
            }, {
                viewState.showError(it)
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("Error subscribing to timer ${it?.message}")
            })
    }

    fun getRatesForDate(date: String): Maybe<ExchangeRates> {
        val observable = model.downloadRatesForDate(date)
            .observeOn(Schedulers.trampoline()) // tests
//             .observeOn(AndroidSchedulers.mainThread()) // run app
//            .subscribeOn(Schedulers.io()) // run app
            .subscribeOn(Schedulers.trampoline()) // tests

        observable.subscribe({
            Logger.getLogger(ExchangeActivity::class.java.name).info("Item received")
            viewState.setUpRecyclerView(it)
        }, {
            viewState.showError(it)
            Logger.getLogger(ExchangeActivity::class.java.name)
                .warning("Failure getting rates for date ${it?.message}")
        }, {
            Logger.getLogger(ExchangeActivity::class.java.name)
                .info("Done getting rates for date")
        })

        return observable
    }
}