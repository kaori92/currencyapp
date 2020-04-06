package com.example.assignment.exchange.presenter

import android.content.Context
import com.example.assignment.core.DefaultStringService
import com.example.assignment.core.LogService
import com.example.assignment.core.StringService
import com.example.assignment.core.TestSchedulerProvider
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.models.ExchangeRatesModel
import com.example.assignment.exchange.view.ExchangeView
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Maybe
import io.reactivex.Observable
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExchangeRatesPresenterTest : Spek({
    val schedulerProvider = TestSchedulerProvider
    val model: ExchangeRatesModel by memoized { mock<ExchangeRatesModel>() }
    val view: ExchangeView by memoized { mock<ExchangeView>() }
    val logger: LogService by memoized { mock<LogService>() }

    val stringService by memoized { mock<StringService>() }

    val presenter: ExchangePresenter by memoized {
        ExchangePresenter(model, schedulerProvider, logger, stringService)
    }

    describe("downloading exchange rates") {
        val rates = ExchangeRates(
            1577795046, "2019-12-31", "USD", mapOf("AED" to 4.125458)
        )

        context("when presenter gets exchange rates") {

            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.just(rates))

                presenter.attachView(view)
                presenter.getExchangeRates()
            }

            it("should call view setUpRecyclerView") {
                verify(view).setUpRecyclerView(rates)
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("error")

            beforeEachTest {
                given(model.downloadExchangeRates()).willReturn(Observable.error(error))

                presenter.attachView(view)
                presenter.getExchangeRates()
            }

            it("should show error") {
                verify(view).showError(any())
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
                given(model.downloadRatesForDate(date)).willReturn(Maybe.just(rates))

                presenter.attachView(view)
                presenter.getRatesForDate(date)
            }

            it("should call view setUpRecyclerView") {
                verify(view).setUpRecyclerView(rates)
            }
        }

        context("when error is returned for date by api") {
            val error =
                Throwable("error")

            beforeEachTest {
                given(model.downloadRatesForDate(date)).willReturn(Maybe.error(error))

                presenter.attachView(view)
                presenter.getRatesForDate(date)
            }

            it("should return empty exchange rates") {
                verify(view).showError(any())
            }
        }
    }
})