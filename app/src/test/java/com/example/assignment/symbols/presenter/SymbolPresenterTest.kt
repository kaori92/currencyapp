package com.example.assignment.symbols.presenter

import com.example.assignment.symbols.data.SymbolsMap
import com.example.assignment.symbols.models.SymbolModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class SymbolPresenterTest : Spek({
    val model: SymbolModel by memoized { mock<SymbolModel>() }

    val presenter: SymbolPresenter by memoized {
        SymbolPresenter(model)
    }

    describe("downloading symbols") {
        lateinit var testObserver: TestObserver<SymbolsMap>

        context("when symbols are provided") {
            val symbols = SymbolsMap(mapOf("PLN" to "Polish Zloty"))

            beforeEachTest {
                given(model.downloadSymbols()).willReturn(Observable.just(symbols))
                testObserver = presenter.getSymbols().test()
            }

            it("should return correct symbols") {
                testObserver.assertComplete()
                testObserver.assertResult(symbols)
            }
        }

        context("when empty symbols are returned by api") {
            beforeEachTest {
                given(model.downloadSymbols()).willReturn(Observable.empty())
                testObserver = presenter.getSymbols().test()
            }

            it("should return empty") {
                testObserver.assertNoValues()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(model.downloadSymbols()).willReturn(Observable.error(error))
                testObserver = presenter.getSymbols().test()
            }

            it("should return empty exchange rates") {
                testObserver.assertError(error)
            }
        }
    }

    describe("downloading symbols flowable") {
        lateinit var testSubscriber: TestSubscriber<SymbolsMap>

        context("when symbols flowable are provided") {
            val symbols = SymbolsMap(mapOf("PLN" to "Polish Zloty"))

            beforeEachTest {
                given(model.downloadSymbols()).willReturn(Observable.just(symbols))
                testSubscriber = presenter.getSymbolsFlowable().test()
            }

            it("should return correct symbols") {
                testSubscriber.assertComplete()
                testSubscriber.assertResult(symbols)
            }
        }

        context("when empty symbols are returned by api") {
            beforeEachTest {
                given(model.downloadSymbols()).willReturn(Observable.empty())
                testSubscriber = presenter.getSymbolsFlowable().test()
            }

            it("should return empty") {
                testSubscriber.assertNoValues()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(model.downloadSymbols()).willReturn(Observable.error(error))
                testSubscriber = presenter.getSymbolsFlowable().test()
            }

            it("should return empty exchange rates") {
                testSubscriber.assertError(error)
            }
        }
    }
})
