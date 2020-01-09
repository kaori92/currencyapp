package com.example.assignment.jsonplaceholder.observers

import android.util.Log
import android.widget.TextView
import com.example.assignment.core.LINE_SEPARATOR
import com.example.assignment.core.TAG
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ExampleObserver(private val name: String, private val textView: TextView) : Observer<Int> {
    override fun onNext(t: Int) {
        append(" $name onNext: value: $t ", textView)
    }

    override fun onComplete() {
        append(" $name onComplete ", textView)
    }

    override fun onSubscribe(d: Disposable) {
        append(" $name onSubscribe: isDisposed ${d.isDisposed} ", textView)
    }


    override fun onError(e: Throwable) {
        append(" $name onError: ${e.message} ", textView)
    }

    private fun append(text: String, textView: TextView) {
        textView.append("$text $LINE_SEPARATOR")
        Log.d(TAG, text)
    }

}