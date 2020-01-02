package com.example.assignment.exchange.presenter

import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.example.assignment.exchange.view.ExchangeView
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

        observable
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showErrorToast(it)
                Logger.getLogger(ExchangeActivity::class.java.name).warning("Failure getting rates ${it?.message}")
            })

        return observable
    }

    fun getExchangeRatesPeriodically(): Disposable {
        return Observable.interval(5, TimeUnit.SECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Logger.getLogger(ExchangeActivity::class.java.name).warning("Getting exchange rates")
                getExchangeRates()
            }, {
                viewState.showErrorToast(it)
                Logger.getLogger(ExchangeActivity::class.java.name).warning("Error subscribing to timer ${it?.message}")
            })
    }

    fun getRatesForDate(date: String) {
        model.downloadRatesForDate(date)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Logger.getLogger(ExchangeActivity::class.java.name).info("Item received")
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showErrorToast(it)
                Logger.getLogger(ExchangeActivity::class.java.name).warning("Failure getting rates for date ${it?.message}")
            }, {
                Logger.getLogger(ExchangeActivity::class.java.name).info("Done getting rates for date")
            })
    }
}