package com.example.assignment.jsonplaceholder.view

import com.example.assignment.core.BaseView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PostView : BaseView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setUpView()
}