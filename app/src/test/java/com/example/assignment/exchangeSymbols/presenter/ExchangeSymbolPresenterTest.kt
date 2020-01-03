package com.example.assignment.exchangeSymbols.presenter

import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchangeSymbols.models.ExchangeSymbolModel
import com.example.assignment.symbols.data.SymbolsMap
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExchangeSymbolPresenterTest : Spek({
    lateinit var testObserver: TestObserver<Array<String>>
    val model: ExchangeSymbolModel by memoized { mock<ExchangeSymbolModel>() }

    val presenter: ExchangeSymbolPresenter by memoized {
        ExchangeSymbolPresenter(model)
    }

    describe("downloading exchange rates and symbols") {

        context("when symbol and rate observables are provided") {
            val rates = ExchangeRates(
                1577795046, "2019-12-31", "EUR", mapOf("PLN" to 4.257214)
            )
            val symbols = SymbolsMap(mapOf("PLN" to "Polish Zloty"))

            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.just(rates))
                given(model.downloadSymbols()).willReturn(Observable.just(symbols))
                testObserver = presenter.getExchangeSymbols().test()
            }

            it("should return correct rates and symbols") {
                testObserver.assertComplete()
                val value = testObserver.values()[0][0]
                val expected = "PLN Polish Zloty 4.257214"

                assert(value == expected)
            }
        }

        context("when empty symbol and rate observables are returned by api") {

            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.empty())
                given(model.downloadSymbols()).willReturn(Observable.empty())
                testObserver = presenter.getExchangeSymbols().test()
            }

            it("should return correct rates and symbols") {
                testObserver.assertNoValues()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.error(error))
                given(model.downloadSymbols()).willReturn(Observable.error(error))
                testObserver = presenter.getExchangeSymbols().test()
            }

            it("should return correct rates and symbols") {
                testObserver.assertError(error)
            }
        }
    }
})