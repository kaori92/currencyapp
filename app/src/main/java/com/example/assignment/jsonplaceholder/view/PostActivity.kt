package com.example.assignment.jsonplaceholder.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.assignment.R
import com.example.assignment.core.BaseActivity
import com.example.assignment.core.MyApplication
import com.example.assignment.jsonplaceholder.presenter.PostPresenter
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

        postPresenter.deletePost(this)
    }

    fun showSubjectExample(view: View) {
        postPresenter.subjectExample(findViewById(R.id.subject_text_view))
    }

    fun showFlatMapExample(view: View) {
        postPresenter.flatMapExample(findViewById(R.id.flat_map_text_view))
    }

    fun showMapExample(view: View) {
        postPresenter.mapExample(findViewById(R.id.map_text_view))
    }

    override fun setUpView() {
        val textView = findViewById<TextView>(R.id.deleted_text_view)
        textView.text = "Deleted"
    }

}