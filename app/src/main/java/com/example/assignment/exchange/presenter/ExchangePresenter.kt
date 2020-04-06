package com.example.assignment.exchange.presenter

import com.example.assignment.R
import com.example.assignment.core.*
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.example.assignment.exchange.view.ExchangeView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import java.util.concurrent.TimeUnit

@InjectViewState
class ExchangePresenter(
    private val model: ExchangeRatesModel,
    private val schedulerProvider: SchedulerProvider,
    private val logger: LogService,
    private val stringService: StringService
) : BasePresenter<ExchangeView>() {
    private var timerDisposable: Disposable? = null

    fun getExchangeRates() {
        val disposable = model.downloadExchangeRates()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showError(it)
                logger.log(
                    ExchangePresenter::class.java.name,
                    "Failure getting rates ${it?.message}"
                )
            })

        compositeDisposable.add(disposable)
    }

    fun diposeOfTimer() {
        timerDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    fun getExchangeRatesPeriodically() {
        getExchangeRates()

        timerDisposable = Observable.interval(TIMER_PERIOD.toLong(), TimeUnit.SECONDS)
            .timeInterval()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                logger.log(ExchangePresenter::class.java.name, "Getting exchange rates")

                viewState.showToast(
                    stringService.getStringResource(
                        R.string.refresh_rates
                    )
                )
                getExchangeRates()
            }, {
                viewState.showError(it)
                logger.log(
                    ExchangePresenter::class.java.name,
                    "Error subscribing to timer ${it?.message}"
                )
            })

        timerDisposable?.let { disposable ->
            compositeDisposable.add(disposable)
        }

    }


    fun getRatesForDate(date: String) {
        val disposable = model.downloadRatesForDate(date)
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showError(it)
                logger.log(
                    ExchangePresenter::class.java.name,
                    "Error getting rates for date ${it?.message}"
                )
            })

        compositeDisposable.add(disposable)
    }
}