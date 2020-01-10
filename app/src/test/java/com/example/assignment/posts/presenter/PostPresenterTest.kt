package com.example.assignment.posts.presenter

import com.example.assignment.core.JSON_API_URL
import com.example.assignment.core.LINE_SEPARATOR
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.core.TEXT_SEPARATOR
import com.example.assignment.posts.models.PostModel
import com.example.assignment.posts.view.PostView
import com.example.assignment.posts.view.`PostView$$State`
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


class PostPresenterTest : Spek({
    val schedulerProvider: SchedulerProvider by memoized { mock<SchedulerProvider>() }
    val model: PostModel by memoized { mock<PostModel>() }
    val view: PostView by memoized { mock<PostView>() }
    val viewState: `PostView$$State` by memoized { mock<`PostView$$State`>() }

    val presenter: PostPresenter by memoized {
        PostPresenter(model, schedulerProvider)
    }

    describe("Deleting posts") {
        context("when presenter deletes post") {
            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                presenter.setViewState(viewState)
                presenter.attachView(view)
                given(model.deletePost()).willReturn(Completable.complete())

                presenter.deletePost()
            }

            it("should call view setUpRecyclerView") {
                verify(viewState).setUpView()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("java.net.UnknownHostException: Unable to resolve host $JSON_API_URL: No address associated with hostname")

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())
                presenter.setViewState(viewState)
                presenter.attachView(view)
                given(model.deletePost()).willReturn(Completable.error(error))

                presenter.deletePost()
            }

            it("should show error") {
                verify(viewState).showError(any())
            }
        }
    }

    describe("Flat map") {
        context("when flat map is called with a text to split ") {
            val textToSplit = "First/Second/Third"
            val first = "First"
            val second = "Second"
            val third = "Third"

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())

                presenter.setViewState(viewState)
                presenter.attachView(view)

                presenter.flatMapExample(textToSplit)
            }

            it("returns correct output") {
                verify(viewState).appendTextFlatMap(first + LINE_SEPARATOR)
                verify(viewState).appendTextFlatMap(second + LINE_SEPARATOR)
                verify(viewState).appendTextFlatMap(third + LINE_SEPARATOR)
            }
        }
    }

    describe("Map") {
        context("when map is called with an observable of integers ") {
            val observable = Observable.range(1, 3)
            val first = 2
            val second = 4
            val third = 6

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())

                presenter.setViewState(viewState)
                presenter.attachView(view)

                presenter.mapExample(observable)
            }

            it("returns correct output") {
                verify(viewState).appendTextMap(first.toString() + LINE_SEPARATOR)
                verify(viewState).appendTextMap(second.toString() + LINE_SEPARATOR)
                verify(viewState).appendTextMap(third.toString() + LINE_SEPARATOR)
            }
        }
    }

    describe("Subject") {
        context("when subject is called with a publish subject of integers ") {
            val source = PublishSubject.create<Int>()

            beforeEachTest {
                given(schedulerProvider.main()).willReturn(Schedulers.trampoline())
                given(schedulerProvider.io()).willReturn(Schedulers.trampoline())

                presenter.setViewState(viewState)
                presenter.attachView(view)

                presenter.subjectExample(source)
            }

            it("returns correct output") {
                verify(viewState).appendTextSubject(
                    """First onSubscribe: isDisposed false${TEXT_SEPARATOR}First onNext: value: 1${TEXT_SEPARATOR}First onNext: value: 2${TEXT_SEPARATOR}First onNext: value: 3${TEXT_SEPARATOR}Second onSubscribe: isDisposed false${TEXT_SEPARATOR}First onNext: value: 4${TEXT_SEPARATOR}Second onNext: value: 4${TEXT_SEPARATOR}First onComplete${TEXT_SEPARATOR}Second onComplete$TEXT_SEPARATOR"""
                )
            }
        }
    }
})