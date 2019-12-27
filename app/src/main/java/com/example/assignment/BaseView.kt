package com.example.assignment

import android.content.Context
import android.widget.Toast
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface BaseView {
    @StateStrategyType(AddToEndStrategy::class)
    fun showError(error: Throwable, context: Context){
        Toast.makeText(context,"Error $error", Toast.LENGTH_LONG).show()
    }
}