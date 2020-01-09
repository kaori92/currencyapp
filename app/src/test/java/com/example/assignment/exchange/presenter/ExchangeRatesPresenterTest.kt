package com.example.assignment.exchange.presenter

import com.example.assignment.core.SchedulerProvider
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.example.assignment.exchange.view.ExchangeView
import com.example.assignment.exchange.view.`ExchangeView$$State`
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExchangeRatesPresenterTest : Spek({
    val schedulerProvider: SchedulerProvider by memoized { mock<SchedulerProvider>() }
    val model: ExchangeRatesModel by memoized { mock<ExchangeRatesModel>() }
    val view: ExchangeView by memoized { mock<ExchangeView>() }
    val viewState: `ExchangeView$$State` by memoized { mock<`ExchangeView$$State`>() }

    val presenter: ExchangePresenter by memoized {
        ExchangePresenter(model, schedulerProvider)
    }

    describe("downloading exchange rates") {
        val rates = ExchangeRates(
            1577795046, "2019-12-31", "USD", mapOf("AED" to 4.125458)
        )

        context("when presenter gets exchange rates") {

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                presenter.setViewState(viewState)
                presenter.attachView(view)

                given(model.downloadExchangeRates()).willReturn(Observable.just(rates))
                presenter.getExchangeRates()
            }

            it("should call view setUpRecyclerView") {
                verify(viewState).setUpRecyclerView(rates)
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable(
                    "java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\":" +
                            " No address associated with hostname"
                )

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                presenter.setViewState(viewState)
                presenter.attachView(view)
                given(model.downloadExchangeRates()).willReturn(Observable.error(error))

                presenter.getExchangeRates()
            }

            it("should show error") {
                verify(viewState).showError(any())
            }
        }
    }

    describe("downloading exchange rates for date") {
        val date = "2013-12-24"
        val rates = ExchangeRates(
            1577795046,
            date,
            "USD",
            mapOf("AED" to 4.125458)
        )

        context("when presenter gets exchange rates for date") {

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                given(model.downloadRatesForDate(date)).willReturn(Maybe.just(rates))
                presenter.setViewState(viewState)
                presenter.attachView(view)

                presenter.getRatesForDate(date)
            }

            it("should call view setUpRecyclerView") {
                verify(viewState).setUpRecyclerView(rates)
            }
        }

        context("when error is returned for date by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host \"data.fixer.io\": No address associated with hostname")

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                presenter.setViewState(viewState)
                presenter.attachView(view)
                given(model.downloadRatesForDate(date)).willReturn(Maybe.error(error))
                presenter.getRatesForDate(date)
            }

            it("should return empty exchange rates") {
                verify(viewState).showError(any())
            }
        }
    }
})