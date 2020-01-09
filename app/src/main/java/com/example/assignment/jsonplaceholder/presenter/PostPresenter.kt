package com.example.assignment.jsonplaceholder.presenter

import android.widget.TextView
import android.widget.Toast
import com.example.assignment.core.SchedulerProvider
import com.example.assignment.jsonplaceholder.models.PostModel
import com.example.assignment.jsonplaceholder.observers.ExampleObserver
import com.example.assignment.jsonplaceholder.view.PostActivity
import com.example.assignment.jsonplaceholder.view.PostView
import io.reactivex.subjects.PublishSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.logging.Logger

@InjectViewState
class PostPresenter(
    private val model: PostModel,
    private val schedulerProvider: SchedulerProvider
) : MvpPresenter<PostView>() {

    fun deletePost(activity: PostActivity) {
        model.deletePost()
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                Toast.makeText(activity, "Deleted post", Toast.LENGTH_SHORT).show()

                viewState.setUpView()
            }, {
                viewState.showError(it)
                Logger.getLogger(PostPresenter::class.java.name)
                    .warning("Failure getting rates ${it?.message}")
            })
    }

    fun subjectExample(textView: TextView) {
        val source = PublishSubject.create<Int>()

        source.subscribe(
            ExampleObserver("First", textView)
        )

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)

        source.subscribe(
            ExampleObserver("Second", textView)
        )

        source.onNext(4)
        source.onComplete()
    }
}