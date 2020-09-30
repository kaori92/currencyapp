package com.example.assignment.post.view

import com.example.assignment.core.BaseView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PostView : BaseView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setUpView()

    @StateStrategyType(AddToEndStrategy::class)
    fun appendTextFlatMap(text: String)

    @StateStrategyType(AddToEndStrategy::class)
    fun appendTextMap(text: String)

    @StateStrategyType(AddToEndStrategy::class)
    fun appendTextSubject(text: String)

    @StateStrategyType(AddToEndStrategy::class)
    fun setTextSwitchMap(text: String)

    @StateStrategyType(AddToEndStrategy::class)
    fun appendTextConcatMap(text: String)
}