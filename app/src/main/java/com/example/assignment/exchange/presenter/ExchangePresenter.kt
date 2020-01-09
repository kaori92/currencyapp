package com.example.assignment.exchange.presenter

import android.widget.Toast
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.core.TIMER_PERIOD
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.example.assignment.exchange.view.ExchangeActivity
import com.example.assignment.exchange.view.ExchangeView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

@InjectViewState
class ExchangePresenter(
    private val model: ExchangeRatesModel,
    private val schedulerProvider: SchedulerProvider
) : MvpPresenter<ExchangeView>() {
    private var disposable: Disposable? = null

    fun getExchangeRates() {
        model.downloadExchangeRates()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showError(it)
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("Failure getting rates ${it?.message}")
            })
    }

    fun diposeOfTimer() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    fun getExchangeRatesPeriodically(activity: ExchangeActivity) {
        disposable = Observable.interval(TIMER_PERIOD.toLong(), TimeUnit.SECONDS)
            .timeInterval()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("Getting exchange rates")
                Toast.makeText(activity, "Refreshing exchange rates", Toast.LENGTH_SHORT).show()
                getExchangeRates()
            }, {
                viewState.showError(it)
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("Error subscribing to timer ${it?.message}")
            })
    }

    fun getRatesForDate(date: String) {
        model.downloadRatesForDate(date)
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showError(it)
            })
    }
}