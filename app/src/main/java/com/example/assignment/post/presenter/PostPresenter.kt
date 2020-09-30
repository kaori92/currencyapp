package com.example.assignment.post.presenter

import com.example.assignment.R
import com.example.assignment.core.*
import com.example.assignment.post.models.PostModel
import com.example.assignment.post.observers.ExampleObserver
import com.example.assignment.post.view.PostView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import moxy.InjectViewState

@InjectViewState
class PostPresenter(
    private val model: PostModel,
    private val schedulerProvider: SchedulerProvider,
    private val logger: LogService,
    private val stringService: StringService
) : BasePresenter<PostView>() {

    fun deletePost() {
        val disposable = model.deletePost()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                viewState.setUpView()
            }, {
                viewState.showError(it)
                logger.log(PostPresenter::class.java.name, "Failure deleting post ${it?.message}")
            })

        compositeDisposable.add(disposable)
    }

    fun subjectExample() {
        val source = PublishSubject.create<Int>()
        val text = StringBuffer("")

        source.subscribe(
            ExampleObserver(stringService.getStringResource(R.string.first), text)
        )

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)

        source.subscribe(
            ExampleObserver(stringService.getStringResource(R.string.second), text)
        )

        source.onNext(4)
        source.onComplete()

        viewState.appendTextSubject(text.toString())

        compositeDisposable.add(source.subscribe())
    }

    fun flatMapExample() {
        val disposable = Observable.just(model.getTextToSplit())
            .flatMap { input -> Observable.fromArray(input.split("/")) }
            .flatMapIterable { items -> items }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({ item ->
                viewState.appendTextFlatMap(item + LINE_SEPARATOR)
            }, {
                logger.log(PostPresenter::class.java.name, "Failure in flat map ${it?.message}")
            })

        compositeDisposable.add(disposable)
    }

    fun mapExample() {
        val disposable = Observable.range(1, 3).map { number -> number * 2 }
            .toList()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({ item ->
                viewState.appendTextMap(item.toString() + LINE_SEPARATOR)
            }, {
                logger.log(PostPresenter::class.java.name, "Failure in map ${it?.message}")
            })

        compositeDisposable.add(disposable)
    }

    fun switchMapExample() {
        val disposable = Observable.fromIterable(listOf("a", "b", "c", "d", "e", "f"))
            .switchMap { input -> Observable.just(input + "x") }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({ item ->
                viewState.setTextSwitchMap(item.toString())
            }, {
                logger.log(PostPresenter::class.java.name, "Failure in switch map ${it?.message}")
            })

        compositeDisposable.add(disposable)
    }

    fun concatMapExample() {
        val disposable = Observable.fromIterable(listOf("a", "b", "c", "d", "e", "f"))
            .concatMap { input -> Observable.just(input + "x") }
            .toList()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({ item ->
                viewState.appendTextConcatMap(item.toString())
            }, {
                logger.log(PostPresenter::class.java.name, "Failure in concat map ${it?.message}")
            })

        compositeDisposable.add(disposable)
    }
}