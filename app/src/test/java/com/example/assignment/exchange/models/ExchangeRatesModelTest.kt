package com.example.assignment.exchange.models

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.exchange.data.ExchangeRates
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExchangeRatesModelTest : Spek({

    val apiService: CurrencyRetrofitService by memoized { mock<CurrencyRetrofitService>() }
    val model: ExchangeRatesModel by memoized {
        DefaultExchangeRatesModel(apiService)
    }
    lateinit var testObserver: TestObserver<ExchangeRates>

    describe("downloading exchange rates") {
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
                Throwable(
                    "java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": " +
                            "No address associated with hostname"
                )

            beforeEachTest {
                given(apiService.getExchangeRates()).willReturn(Observable.error(error))
                testObserver = model.downloadExchangeRates().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }

    describe("downloading exchange rates for date") {
        val date = "2013-12-24"
        val ratesMap = mapOf("AED" to 4.125458)
        val rates = ExchangeRates(1577795046, date, "USD", ratesMap)

        context("when correct exchange rates is returned for date by api") {
            beforeEachTest {
                given(apiService.getRatesForDate(date)).willReturn(Maybe.just(rates))
                testObserver = model.downloadRatesForDate(date).test()
            }

            it("should return correct exchange rates for date") {
                testObserver.assertComplete()
                testObserver.assertResult(rates)
            }
        }

        context("when empty exchange rates is returned for date by api") {

            beforeEachTest {
                given(apiService.getRatesForDate(date)).willReturn(Maybe.empty())
                testObserver = model.downloadRatesForDate(date).test()
            }

            it("should return empty exchange rates") {
                testObserver.assertNoValues()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(apiService.getRatesForDate(date)).willReturn(Maybe.error(error))
                testObserver = model.downloadRatesForDate(date).test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }
})