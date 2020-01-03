package com.example.assignment.exchange.presenter

import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExchangeRatesPresenterTest : Spek({
    lateinit var testObserver: TestObserver<ExchangeRates>
    val model: ExchangeRatesModel by memoized { mock<ExchangeRatesModel>() }

    val presenter: ExchangePresenter by memoized {
        ExchangePresenter(model)
    }

    describe("downloading exchange rates") {
        val rates = ExchangeRates(
            1577795046, "2019-12-31", "USD", mapOf("AED" to 4.125458)
        )

        context("when correct exchange rates object is returned by api") {

            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.just(rates))
                testObserver = presenter.getExchangeRates().test()
            }

            it("should get the same exchange rates") {
                testObserver.assertComplete()
                testObserver.assertResult(rates)
            }
        }

        context("when empty object is returned by api") {
            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.empty())
                testObserver = presenter.getExchangeRates().test()
            }

            it("should return empty") {
                testObserver.assertNoValues()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.error(error))
                testObserver = presenter.getExchangeRates().test()
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

        context("when correct exchange rates object for date is returned by api") {

            beforeEachTest {
                given(model.downloadRatesForDate(date)).willReturn(Maybe.just(rates))
                testObserver = presenter.getRatesForDate(date).test()
            }

            it("should get the same exchange rates") {
                testObserver.assertComplete()
                testObserver.assertResult(rates)
            }
        }

        context("when empty object is returned for date by api") {
            beforeEachTest {
                given(model.downloadRatesForDate(date)).willReturn(Maybe.empty())
                testObserver = presenter.getRatesForDate(date).test()
            }

            it("should return empty") {
                testObserver.assertNoValues()
            }
        }

        context("when error is returned for date by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(model.downloadRatesForDate(date)).willReturn(Maybe.error(error))
                testObserver = presenter.getRatesForDate(date).test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }
})