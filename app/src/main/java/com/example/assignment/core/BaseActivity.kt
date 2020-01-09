package com.example.assignment.core

import android.util.Log
import android.widget.Toast
import moxy.MvpAppCompatActivity

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    override fun showError(error: Throwable) {
        Toast.makeText(baseContext, "Error $error", Toast.LENGTH_LONG).show()
        Log.e("TAG", "Error $error")
    }
}