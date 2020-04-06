package com.example.assignment.exchangeSymbols.models

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.symbols.data.SymbolsMap
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

class DefaultExchangeSymbolModelTest : Spek({
    val apiService: CurrencyRetrofitService by memoized { mock<CurrencyRetrofitService>() }

    val model: ExchangeSymbolModel by memoized {
        DefaultExchangeSymbolModel(apiService)
    }

    describe("downloading exchange rates") {
        lateinit var testObserver: TestObserver<Array<String>>
        val ratesMap = mapOf("AED" to 4.125458)
        val rates = Observable.just(ExchangeRates(1577795046, "2019-12-31", "USD", ratesMap))
        val symbols = Single.just(SymbolsMap(mapOf("PLN" to "Polish Zloty")))
        val expected = arrayOf("PLN Polish Zloty null")

        context("when correct exchange rates is returned by api") {
            beforeEachTest {
                given(apiService.getExchangeRates()).willReturn(rates)
                given(apiService.getSymbols()).willReturn(symbols)

                testObserver = model.downloadExchangeSymbols().test()
            }

            it("should complete") {
                testObserver.assertComplete()
            }

            it("should return correct exchange rates and symbols") {
                val areEqual: (Array<String>) -> Boolean = { array -> array.contentEquals(expected) }
                testObserver.assertValue(areEqual)
            }
        }

        context("when error is returned by api") {
            val error = Throwable("error")

            beforeEachTest {
                given(apiService.getExchangeRates()).willReturn(Observable.error(error))
                given(apiService.getSymbols()).willReturn(symbols)

                testObserver = model.downloadExchangeSymbols().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }
})