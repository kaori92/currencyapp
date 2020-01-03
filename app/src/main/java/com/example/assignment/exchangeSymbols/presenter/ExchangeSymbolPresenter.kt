package com.example.assignment.exchangeSymbols.presenter

import com.example.assignment.exchange.activities.ExchangeActivity
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchangeSymbols.models.ExchangeSymbolModel
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolView
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.logging.Logger

@InjectViewState
class ExchangeSymbolPresenter(
    private val model: ExchangeSymbolModel
) : MvpPresenter<ExchangeSymbolView>() {

    fun getExchangeSymbols(): Observable<Array<String>> {
        val symbolsObservable: Observable<SymbolsMap> = model.downloadSymbols()

        val ratesObservable: Observable<ExchangeRates> = model.downloadExchangeRates()

        val zippedObservable = Observable.zip(symbolsObservable, ratesObservable,
            BiFunction<SymbolsMap, ExchangeRates, Array<String>> { symbolsMap, rates ->
                combineSymbolWithRate(symbolsMap, rates)
            }
        )

        zippedObservable
            .observeOn(Schedulers.trampoline()) // tests
            .subscribeOn(Schedulers.trampoline()) // tests
//            .observeOn(AndroidSchedulers.mainThread()) // run app
//            .subscribeOn(Schedulers.io()) // run app
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showErrorToast(it)
                Logger.getLogger(ExchangeActivity::class.java.name)
                    .warning("Failure getting rates ${it?.message}")
            })

        return zippedObservable
    }

    private fun combineSymbolWithRate(symbols: SymbolsMap, rates: ExchangeRates): Array<String> {
        val unionList = (symbols.map.asSequence() + rates.rates.asSequence())
            .distinct()
            .groupBy({ it.key }, { it.value })
            .mapValues { (_, values) -> values.joinToString(" ") }

        return unionList.entries.map { (key, value) -> "$key $value" }.toTypedArray()
    }
}