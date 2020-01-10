package com.example.assignment.posts.presenter

import android.util.Log
import com.example.assignment.core.LINE_SEPARATOR
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.core.TAG
import com.example.assignment.posts.models.PostModel
import com.example.assignment.posts.observers.ExampleObserver
import com.example.assignment.posts.view.PostView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.logging.Logger

@InjectViewState
class PostPresenter(
    private val model: PostModel,
    private val schedulerProvider: SchedulerProvider
) : MvpPresenter<PostView>() {

    fun deletePost() {
        model.deletePost()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setUpView()
            }, {
                viewState.showError(it)
                Logger.getLogger(PostPresenter::class.java.name)
                    .warning("Failure getting rates ${it?.message}")
            })
    }

    fun subjectExample(source: PublishSubject<Int>) {
        val text = StringBuffer("")

        source.subscribe(
            ExampleObserver("First", text)
        )

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)

        source.subscribe(
            ExampleObserver("Second", text)
        )

        source.onNext(4)
        source.onComplete()

        viewState.appendTextSubject(text.toString())
    }

    fun flatMapExample(textToSplit: String) {
        Observable.just(textToSplit)
            .flatMap { input -> Observable.fromArray(input.split("/")) }
            .flatMapIterable { items -> items }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({ item ->
                viewState.appendTextFlatMap(item + LINE_SEPARATOR)
            }, {
                Log.d(TAG, "FAILURE: ${it.message}")
            })
    }

    fun mapExample(observable: Observable<Int>) {
        observable.map { number -> number * 2 }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({ item ->
                viewState.appendTextMap(item.toString() + LINE_SEPARATOR)
            }, {
                Log.d(TAG, "FAILURE: ${it.message}")
            })
    }
}