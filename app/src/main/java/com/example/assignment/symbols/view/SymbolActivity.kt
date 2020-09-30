package com.example.assignment.symbols.view

import android.os.Bundle
import com.example.assignment.R
import com.example.assignment.core.BaseActivity
import com.example.assignment.core.MyApplication
import com.example.assignment.core.PREF_BASE
import com.example.assignment.di.AppModule
import com.example.assignment.di.ApplicationComponent
import com.example.assignment.di.DaggerApplicationComponent
import com.example.assignment.symbols.data.SymbolsMap
import com.example.assignment.symbols.di.DaggerSymbolComponent
import com.example.assignment.symbols.di.SymbolComponent
import com.example.assignment.symbols.di.SymbolModule
import com.example.assignment.symbols.presenter.SymbolPresenter
import kotlinx.android.synthetic.main.activity_symbol.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SymbolActivity : BaseActivity(), SymbolView {

    private val appComponent by lazy {
        (applicationContext as MyApplication).appComponent
    }
    private val symbolComponent: SymbolComponent by lazy {
        DaggerSymbolComponent
            .builder()
            .symbolModule(SymbolModule)
            .applicationComponent(appComponent)
            .build()
    }

    @ProvidePresenter
    fun providePresenter(): SymbolPresenter = symbolComponent.presenter()

    @InjectPresenter
    lateinit var symbolPresenter: SymbolPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symbol)
        print(applicationContext)

        symbolPresenter.getSymbols()
    }

    override fun setSymbols(symbolsMap: SymbolsMap) {
        val base = intent.getStringExtra(PREF_BASE).orEmpty()
        baseTextView.text = base
        nameTextView.text = symbolsMap.map[base]
    }

}