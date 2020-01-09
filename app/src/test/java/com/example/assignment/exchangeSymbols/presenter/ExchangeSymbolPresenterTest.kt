package com.example.assignment.exchangeSymbols.presenter

import com.example.assignment.core.AndroidSchedulerProvider
import com.example.assignment.exchangeSymbols.models.ExchangeSymbolModel
import com.example.assignment.exchangeSymbols.view.`ExchangeSymbolView$$State`
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExchangeSymbolPresenterTest : Spek({
    val model: ExchangeSymbolModel by memoized { mock<ExchangeSymbolModel>() }
    val viewState: `ExchangeSymbolView$$State` by memoized { mock<`ExchangeSymbolView$$State`>() }
    val presenter: ExchangeSymbolPresenter by memoized {
        ExchangeSymbolPresenter(model)
    }

    describe("downloading exchange rates and symbols") {
        context("when presenter gets called with getExchangeSymbols") {
            beforeEachTest {
                presenter.setViewState(viewState)
                presenter.getExchangeSymbols()
            }

            it("should call models function") {
                verify(model).combineRatesWithSymbols(viewState, AndroidSchedulerProvider)
            }
        }
    }
})