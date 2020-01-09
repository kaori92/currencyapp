package com.example.assignment.symbols.presenter

import com.example.assignment.core.SchedulerProvider
import com.example.assignment.symbols.data.SymbolsMap
import com.example.assignment.symbols.models.SymbolModel
import com.example.assignment.symbols.view.SymbolView
import com.example.assignment.symbols.view.`SymbolView$$State`
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class SymbolPresenterTest : Spek({
    val schedulerProvider: SchedulerProvider by memoized { mock<SchedulerProvider>() }
    val model: SymbolModel by memoized { mock<SymbolModel>() }
    val view: SymbolView by memoized { mock<SymbolView>() }
    val viewState: `SymbolView$$State` by memoized { mock<`SymbolView$$State`>() }

    val presenter: SymbolPresenter by memoized {
        SymbolPresenter(model, schedulerProvider)
    }

    describe("downloading symbols") {

        context("when presenter gets symbols") {
            val symbols = SymbolsMap(mapOf("PLN" to "Polish Zloty"))

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                presenter.setViewState(viewState)
                presenter.attachView(view)
                given(model.downloadSymbols()).willReturn(Single.just(symbols))

                presenter.getSymbols()
            }

            it("should call view setUpRecyclerView") {
                verify(viewState).setSymbols(symbols)
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
                given(model.downloadSymbols()).willReturn(Single.error(error))

                presenter.getSymbols()
            }

            it("should show error") {
                verify(viewState).showError(any())
            }
        }
    }

})
