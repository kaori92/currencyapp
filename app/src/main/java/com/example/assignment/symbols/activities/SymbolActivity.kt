package com.example.assignment.symbols.activities

import android.os.Bundle
import com.example.assignment.MyApplicationComponent
import com.example.assignment.R
import com.example.assignment.symbols.SymbolView
import com.example.assignment.symbols.data.SymbolsMap
import com.example.assignment.symbols.presenter.SymbolPresenter
import kotlinx.android.synthetic.main.activity_symbol.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SymbolActivity : MvpAppCompatActivity(), SymbolView {

    @ProvidePresenter
    fun providePresenter(): SymbolPresenter = component
        .presenter()

    @InjectPresenter
    lateinit var symbolPresenter: SymbolPresenter

    private val component by lazy {
        (applicationContext as MyApplicationComponent)
            .appComponent.requestSymbolComponentBuilder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symbol)

        //symbolPresenter.getSymbols()
        symbolPresenter.getSymbolsFlowable()
    }

    override fun setSymbols(symbolsMap: SymbolsMap) {
        val base :String? = intent.getStringExtra("base")
        baseTextView.setText(base)
        nameTextView.setText(symbolsMap.map.get(base))
    }

    override fun showErrorToast(error: Throwable) {
        showError(error, this)
    }

}