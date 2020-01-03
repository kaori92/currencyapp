package com.example.assignment.symbols.models

import com.example.assignment.retrofit.CurrencyRetrofitService
import com.example.assignment.symbols.data.SymbolsMap
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class DefaultSymbolModelTest : Spek({
    lateinit var testObserver: TestObserver<SymbolsMap>
    val apiService: CurrencyRetrofitService by memoized { mock<CurrencyRetrofitService>() }
    val model: SymbolModel by memoized {
        DefaultSymbolModel(apiService)
    }

    describe("downloading symbols") {
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