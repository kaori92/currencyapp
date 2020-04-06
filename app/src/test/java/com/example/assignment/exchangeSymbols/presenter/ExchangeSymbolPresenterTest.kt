package com.example.assignment.exchangeSymbols.presenter

import com.example.assignment.core.LogService
import com.example.assignment.core.TestSchedulerProvider
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchangeSymbols.models.ExchangeSymbolModel
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolView
import com.example.assignment.symbols.data.SymbolsMap
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExchangeSymbolPresenterTest : Spek({

    val schedulerProvider = TestSchedulerProvider
    val model: ExchangeSymbolModel by memoized { mock<ExchangeSymbolModel>() }
    val view: ExchangeSymbolView by memoized { mock<ExchangeSymbolView>() }
    val logger: LogService by memoized { mock<LogService>() }

    val presenter: ExchangeSymbolPresenter by memoized {
        ExchangeSymbolPresenter(model, schedulerProvider, logger)
    }

    describe("combining exchange rates with symbols") {

        context("when exchange rates and symbols are downloaded ") {
            val expected = arrayOf("PLN Polish Zloty 4.257214")
            val expectedObservable = Observable.just(expected)

            beforeEachTest {
                given(model.downloadExchangeSymbols()).willReturn(expectedObservable)

                presenter.attachView(view)
                presenter.getExchangeSymbols()
            }

            it("should return correct rates and symbols") {
                verify(view).setUpRecyclerView(expected)
            }
        }


        context("when error is returned by api") {
            val error = Throwable("error")

            beforeEachTest {
                given(model.downloadExchangeSymbols()).willReturn(Observable.error(error))

                presenter.attachView(view)
                presenter.getExchangeSymbols()
            }

            it("should return correct rates and symbols") {
                verify(view).showError(error)
            }
        }
    }
})