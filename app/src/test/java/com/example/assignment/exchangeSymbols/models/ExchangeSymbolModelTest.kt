package com.example.assignment.exchangeSymbols.models

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.SymbolsMap
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class DefaultExchangeSymbolModelTest : Spek({
    val apiService: CurrencyRetrofitService by memoized { mock<CurrencyRetrofitService>() }
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
                given(apiService.getSymbols()).willReturn(Observable.just(symbols))
                testObserver = model.downloadSymbols().test()
            }

            it("should return correct symbols") {
                testObserver.assertComplete()
                testObserver.assertResult(symbols)
            }
        }

        context("when empty symbols are returned by api") {

            beforeEachTest {
                given(apiService.getSymbols()).willReturn(Observable.empty())
                testObserver = model.downloadSymbols().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertNoValues()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(apiService.getSymbols()).willReturn(Observable.error(error))
                testObserver = model.downloadSymbols().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }
})