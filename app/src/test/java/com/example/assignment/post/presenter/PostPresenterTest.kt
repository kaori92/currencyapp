package com.example.assignment.post.presenter

import com.example.assignment.R
import com.example.assignment.core.*
import com.example.assignment.post.models.PostModel
import com.example.assignment.post.view.PostView
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class PostPresenterTest : Spek({

    val schedulerProvider = TestSchedulerProvider
    val model: PostModel by memoized { mock<PostModel>() }
    val view: PostView by memoized { mock<PostView>() }
    val logger: LogService by memoized { mock<LogService>() }
    val stringService = mock<StringService>()

    val presenter: PostPresenter by memoized {
        PostPresenter(model, schedulerProvider, logger, stringService)
    }

    describe("Deleting posts") {
        context("when presenter deletes post") {
            beforeEachTest {
                given(model.deletePost()).willReturn(Completable.complete())

                presenter.attachView(view)
                presenter.deletePost()
            }

            it("should call view setUpRecyclerView") {
                verify(view).setUpView()
            }
        }

        context("when error is returned by api") {
            val error =
                Throwable("error")

            beforeEachTest {
                given(model.deletePost()).willReturn(Completable.error(error))

                presenter.attachView(view)
                presenter.deletePost()
            }

            it("should show error") {
                verify(view).showError(any())
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
                given(model.getTextToSplit()).willReturn(textToSplit)

                presenter.attachView(view)
                presenter.flatMapExample()
            }

            it("should return correct first element") {
                verify(view).appendTextFlatMap(first + LINE_SEPARATOR)
            }

            it("should return correct second element") {
                verify(view).appendTextFlatMap(second + LINE_SEPARATOR)
            }

            it("should return correct third element") {
                verify(view).appendTextFlatMap(third + LINE_SEPARATOR)
            }
        }
    }

    describe("Switch map") {
        context("when switch map is called with a list of characters") {

            val expected = "fx"

            beforeEachTest {
                presenter.attachView(view)
                presenter.switchMapExample()
            }

            it("should return correct output") {
                verify(view).setTextSwitchMap(expected)
            }
        }
    }

    describe("Concat map") {
        context("when concat map is called with a list of characters") {

            val expected = "[ax, bx, cx, dx, ex, fx]"

            beforeEachTest {
                presenter.attachView(view)
                presenter.concatMapExample()
            }

            it("should return correct output") {
                verify(view).appendTextConcatMap(expected)
            }
        }
    }

    describe("Map") {
        context("when map is called with an observable of integers ") {
            val expected = "[2, 4, 6]"

            beforeEachTest {
                presenter.attachView(view)
                presenter.mapExample()
            }

            it("should return correct output") {
                verify(view).appendTextMap(expected + LINE_SEPARATOR)
            }
        }
    }

    describe("Subject") {
        context("when subject is called with a publish subject of integers ") {

            beforeEachTest {
                given(stringService.getStringResource(R.string.first)).willReturn("First")
                given(stringService.getStringResource(R.string.second)).willReturn("Second")

                presenter.attachView(view)
                presenter.subjectExample()
            }

            it("should return correct output") {
                verify(view).appendTextSubject(
                    """First onSubscribe: isDisposed false${TEXT_SEPARATOR}First onNext: value: 1${TEXT_SEPARATOR}First onNext: value: 2${TEXT_SEPARATOR}First onNext: value: 3${TEXT_SEPARATOR}Second onSubscribe: isDisposed false${TEXT_SEPARATOR}First onNext: value: 4${TEXT_SEPARATOR}Second onNext: value: 4${TEXT_SEPARATOR}First onComplete${TEXT_SEPARATOR}Second onComplete$TEXT_SEPARATOR"""
                )
            }
        }
    }
})