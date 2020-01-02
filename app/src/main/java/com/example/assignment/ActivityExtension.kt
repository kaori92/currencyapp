package com.example.assignment

import android.content.Context
import android.widget.Toast
import moxy.MvpAppCompatActivity

fun MvpAppCompatActivity.showError(error: Throwable, context: Context){
    Toast.makeText(context,"Error $error", Toast.LENGTH_LONG).show()
}