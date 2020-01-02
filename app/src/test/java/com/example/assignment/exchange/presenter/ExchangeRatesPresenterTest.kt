package com.example.assignment.exchange.presenter

import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExchangeRatesPresenterTest: Spek({

    val model: ExchangeRatesModel by memoized { mock<ExchangeRatesModel>() }

    val presenter: ExchangePresenter by memoized {
        ExchangePresenter(model)
    }

    describe("downloading exchange rates"){
        val ratesMap = mapOf("AED" to 4.125458)
        val rates = ExchangeRates(1577795046, "2019-12-31","USD", ratesMap)
        lateinit var testObserver: TestObserver<ExchangeRates>

        context("when correct exchange rates object is returned by api"){

            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.just(rates))
                testObserver = presenter.getExchangeRates().test()
            }

            it("should get some exchange rates"){
                testObserver.assertComplete()
            }
        }
    }

})