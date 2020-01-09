package com.example.assignment.exchangeSymbols.models

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchangeSymbols.presenter.ExchangeSymbolPresenter
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolView
import com.example.assignment.exchangeSymbols.view.`ExchangeSymbolView$$State`
import com.example.assignment.symbols.data.SymbolsMap
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class DefaultExchangeSymbolModelTest : Spek({
    val schedulerProvider: SchedulerProvider by memoized { mock<SchedulerProvider>() }
    val apiService: CurrencyRetrofitService by memoized { mock<CurrencyRetrofitService>() }
    val view: ExchangeSymbolView by memoized { mock<ExchangeSymbolView>() }
    val viewState: `ExchangeSymbolView$$State` by memoized { mock<`ExchangeSymbolView$$State`>() }

    val model: ExchangeSymbolModel by memoized {
        DefaultExchangeSymbolModel(apiService)
    }

    describe("downloading exchange rates") {
        lateinit var testObserver: TestObserver<ExchangeRates>
        val ratesMap = mapOf("AED" to 4.125458)
        val rates = ExchangeRates(1577795046, "2019-12-31", "USD", ratesMap)

        context("when correct exchange rates is returned by api") {
            beforeEachTest {
                given(apiService.getExchangeRates()).willReturn(Observable.just(rates))
                testObserver = model.downloadExchangeRates().test()
            }

            it("should return correct exchange rates") {
                testObserver.assertComplete()
                testObserver.assertResult(rates)
            }
        }

        context("when empty exchange rates is returned by api") {

            beforeEachTest {
                given(apiService.getExchangeRates()).willReturn(Observable.empty())
                testObserver = model.downloadExchangeRates().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertNoValues()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(apiService.getExchangeRates()).willReturn(Observable.error(error))
                testObserver = model.downloadExchangeRates().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }

    describe("downloading symbols") {
        lateinit var testObserver: TestObserver<SymbolsMap>
        val symbols = SymbolsMap(mapOf("PLN" to "Polish Zloty"))

        context("when correct symbols is returned by api") {
            beforeEachTest {
                given(apiService.getSymbols()).willReturn(Single.just(symbols))
                testObserver = model.downloadSymbols().test()
            }

            it("should return correct symbols") {
                testObserver.assertComplete()
                testObserver.assertResult(symbols)
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(apiService.getSymbols()).willReturn(Single.error(error))
                testObserver = model.downloadSymbols().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }

    describe("combining exchange rates with symbols") {
        val presenter: ExchangeSymbolPresenter by memoized {
            ExchangeSymbolPresenter(model)
        }

        context("when exchange rates and symbols are downloaded ") {
            val rates = ExchangeRates(
                1577795046, "2019-12-31", "EUR", mapOf("PLN" to 4.257214)
            )
            val symbols = SymbolsMap(mapOf("PLN" to "Polish Zloty"))

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                presenter.setViewState(viewState)
                presenter.attachView(view)

                given(model.downloadExchangeRates()).willReturn(Observable.just(rates))
                given(model.downloadSymbols()).willReturn(Single.just(symbols))

                model.combineRatesWithSymbols(viewState, schedulerProvider)
            }

            it("should return correct rates and symbols") {
                val expected = arrayOf("PLN Polish Zloty 4.257214")

                verify(viewState).setUpRecyclerView(expected)
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                presenter.setViewState(viewState)
                presenter.attachView(view)

                given(model.downloadExchangeRates()).willReturn(Observable.error(error))
                given(model.downloadSymbols()).willReturn(Single.error(error))

                model.combineRatesWithSymbols(viewState, schedulerProvider)
            }

            it("should return correct rates and symbols") {
                verify(viewState).showError(error)
            }
        }
    }
})