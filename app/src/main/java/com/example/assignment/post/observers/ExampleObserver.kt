package com.example.assignment.post.observers

import com.example.assignment.core.TEXT_SEPARATOR
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ExampleObserver(private val name: String, private val appendableString: StringBuffer) :
    Observer<Int> {
    override fun onNext(t: Int) {
        appendableString.append("$name onNext: value: $t$TEXT_SEPARATOR")
    }

    override fun onComplete() {
        appendableString.append("$name onComplete$TEXT_SEPARATOR")
    }

    override fun onSubscribe(d: Disposable) {
        appendableString.append("$name onSubscribe: isDisposed ${d.isDisposed}$TEXT_SEPARATOR")
    }


    override fun onError(e: Throwable) {
        appendableString.append("$name onError: ${e.message}$TEXT_SEPARATOR")
    }
}