package com.example.assignment.posts.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.assignment.R
import com.example.assignment.core.BaseActivity
import com.example.assignment.core.MyApplication
import com.example.assignment.posts.presenter.PostPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class PostActivity : BaseActivity(), PostView {

    private lateinit var deletedTextView: TextView
    private lateinit var flatMapTextView: TextView
    private lateinit var concatMapTextView: TextView
    private lateinit var mapTextView: TextView
    private lateinit var subjectTextView: TextView
    private lateinit var switchMapTextView: TextView

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

        deletedTextView = findViewById(R.id.deleted_text_view)
        flatMapTextView = findViewById(R.id.flat_map_text_view)
        concatMapTextView = findViewById(R.id.concat_map_text_view)
        mapTextView = findViewById(R.id.map_text_view)
        subjectTextView = findViewById(R.id.subject_text_view)
        switchMapTextView = findViewById(R.id.switch_map_text_view)
    }

    override fun onResume() {
        super.onResume()

        postPresenter.deletePost()
    }

    fun showSubjectExample(view: View) {
        postPresenter.subjectExample()
    }

    fun showFlatMapExample(view: View) {
        postPresenter.flatMapExample()
    }

    fun showMapExample(view: View) {
        postPresenter.mapExample()
    }

    fun showSwitchMapExample(view: View) {
        postPresenter.switchMapExample()
    }

    fun showConcatMapExample(view: View) {
        postPresenter.concatMapExample()
    }

    override fun setUpView() {
        deletedTextView.text = getString(R.string.deleted)
    }

    override fun appendTextFlatMap(text: String) {
        flatMapTextView.append(text)
    }

    override fun appendTextConcatMap(text: String) {
        concatMapTextView.append(text)
    }

    override fun appendTextMap(text: String) {
        mapTextView.append(text)
    }

    override fun appendTextSubject(text: String) {
        subjectTextView.append(text)
    }

    override fun setTextSwitchMap(text: String) {
        switchMapTextView.text = text
    }

}