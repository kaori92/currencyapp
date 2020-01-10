package com.example.assignment.posts.observers

import com.example.assignment.core.LINE_SEPARATOR
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ExampleObserver(private val name: String, private val appendableString: StringBuffer) :
    Observer<Int> {
    override fun onNext(t: Int) {
        appendableString.append(" $name onNext: value: $t$LINE_SEPARATOR")
    }

    override fun onComplete() {
        appendableString.append(" $name onComplete$LINE_SEPARATOR")
    }

    override fun onSubscribe(d: Disposable) {
        appendableString.append(" $name onSubscribe: isDisposed ${d.isDisposed}$LINE_SEPARATOR")
    }


    override fun onError(e: Throwable) {
        appendableString.append(" $name onError: ${e.message}$LINE_SEPARATOR")
    }
}