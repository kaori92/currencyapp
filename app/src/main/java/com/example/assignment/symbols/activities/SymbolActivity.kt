package com.example.assignment.symbols.activities

import android.os.Bundle
import com.example.assignment.core.MyApplication
import com.example.assignment.R
import com.example.assignment.core.BaseActivity
import com.example.assignment.symbols.SymbolView
import com.example.assignment.symbols.data.SymbolsMap
import com.example.assignment.symbols.presenter.SymbolPresenter
import kotlinx.android.synthetic.main.activity_symbol.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SymbolActivity : BaseActivity(), SymbolView {

    @ProvidePresenter
    fun providePresenter(): SymbolPresenter = component.presenter()

    @InjectPresenter
    lateinit var symbolPresenter: SymbolPresenter

    private val component by lazy {
        (applicationContext as MyApplication).appComponent
            .requestSymbolComponentBuilder()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symbol)

        //symbolPresenter.getSymbols()
        symbolPresenter.getSymbolsFlowable()
    }

    override fun setSymbols(symbolsMap: SymbolsMap) {
        val base = intent.getStringExtra("base").orEmpty()
        baseTextView.text = base
        nameTextView.text = symbolsMap.map[base]
    }

}