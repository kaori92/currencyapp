package com.example.assignment.exchange.presenter

import com.example.assignment.exchange.view.ExchangeView
import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.exchange.models.ExchangeRatesModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.logging.Logger

@InjectViewState
class ExchangePresenter(
    private val model: ExchangeRatesModel
) : MvpPresenter<ExchangeView>() {

    fun getExchangeRates() {
        model.downloadExchangeRates()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
//                viewState.showError(it)
                Logger.getLogger(ExchangeActivity::class.java.name).warning("failure ${it?.message}")
            })
    }
}