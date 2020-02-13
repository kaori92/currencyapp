package com.example.assignment.symbols.presenter

import com.example.assignment.core.LogService
import com.example.assignment.core.TestSchedulerProvider
import com.example.assignment.symbols.data.SymbolsMap
import com.example.assignment.symbols.models.SymbolModel
import com.example.assignment.symbols.view.SymbolView
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class SymbolPresenterTest : Spek({

    val schedulerProvider = TestSchedulerProvider
    val model: SymbolModel by memoized { mock<SymbolModel>() }
    val view: SymbolView by memoized { mock<SymbolView>() }
    val logger: LogService by memoized { mock<LogService>() }

    val presenter: SymbolPresenter by memoized {
        SymbolPresenter(model, schedulerProvider, logger)
    }

    describe("downloading symbols") {

        context("when presenter gets symbols") {
            val symbols = SymbolsMap(mapOf("PLN" to "Polish Zloty"))

            beforeEachTest {
                given(model.downloadSymbols()).willReturn(Single.just(symbols))

                presenter.attachView(view)
                presenter.getSymbols()
            }

            it("should call view setUpRecyclerView") {
                verify(view).setSymbols(symbols)
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("error")

            beforeEachTest {
                given(model.downloadSymbols()).willReturn(Single.error(error))

                presenter.attachView(view)
                presenter.getSymbols()
            }

            it("should show error") {
                verify(view).showError(any())
            }
        }
    }

})
