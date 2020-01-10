package com.example.assignment.posts.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.assignment.R
import com.example.assignment.core.BaseActivity
import com.example.assignment.core.MyApplication
import com.example.assignment.posts.presenter.PostPresenter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class PostActivity : BaseActivity(), PostView {

    private val component by lazy {
        (applicationContext as MyApplication).appComponent
            .requestPostComponentBuilder()
            .build()
    }

    @ProvidePresenter
    fun providePresenter(): PostPresenter = component.postPresenter()

    @InjectPresenter
    lateinit var postPresenter: PostPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
    }

    override fun onResume() {
        super.onResume()

        postPresenter.deletePost()
    }

    fun showSubjectExample(view: View) {
        val source = PublishSubject.create<Int>()
        postPresenter.subjectExample(source)
    }

    fun showFlatMapExample(view: View) {
        val textToSplit = "First/Second/Third"
        postPresenter.flatMapExample(textToSplit)
    }

    fun showMapExample(view: View) {
        val observable = Observable.range(1, 6)
        postPresenter.mapExample(observable)
    }

    fun showSwitchMapExample(view: View) {
        val list = listOf("a", "b", "c", "d", "e", "f")
        postPresenter.switchMapExample(list)
    }

    fun showConcatMapExample(view: View) {
        val list = listOf("a", "b", "c", "d", "e", "f")
        postPresenter.concatMapExample(list)
    }

    override fun setUpView() {
        val textView = findViewById<TextView>(R.id.deleted_text_view)
        textView.text = "Deleted"
    }

    override fun appendTextFlatMap(text: String) {
        findViewById<TextView>(R.id.flat_map_text_view).append(text)
    }

    override fun appendTextConcatMap(text: String) {
        findViewById<TextView>(R.id.concat_map_text_view).append(text)
    }

    override fun appendTextMap(text: String) {
        findViewById<TextView>(R.id.map_text_view).append(text)
    }

    override fun appendTextSubject(text: String) {
        findViewById<TextView>(R.id.subject_text_view).append(text)
    }

    override fun setTextSwitchMap(text: String) {
        findViewById<TextView>(R.id.switch_map_text_view).text = text
    }

}