package com.example.assignment.exchangeSymbols.models

import android.util.Log
import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolView
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables

class DefaultExchangeSymbolModel(
    private val apiService: CurrencyRetrofitService
) : ExchangeSymbolModel {

    override fun downloadExchangeRates(): Observable<ExchangeRates> {
        return apiService.getExchangeRates()
    }

    override fun downloadSymbols(): Single<SymbolsMap> {
        return apiService.getSymbols()
    }

    override fun combineRatesWithSymbols(
        viewState: ExchangeSymbolView,
        schedulerProvider: SchedulerProvider
    ) {
        Observables.zip(
            downloadSymbols().toObservable(),
            downloadExchangeRates()
        )
        { symbolsMap, rates ->
            combineSymbolWithRate(symbolsMap, rates)
        }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setUpRecyclerView(it)
            }, {
                viewState.showError(it)
                Log.e("TAG", "Failure getting rates ${it?.message}")
            })
    }

    private fun combineSymbolWithRate(symbols: SymbolsMap, rates: ExchangeRates): Array<String> {
        val list = mutableListOf<String>()
        symbols.map.forEach { (base, name) ->
            val rate = rates.rates.get(base)
            list.add("$base $name $rate")
        }

        return list.toTypedArray()
    }
}
