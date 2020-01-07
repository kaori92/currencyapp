package com.example.assignment.exchangeSymbols.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.core.BaseActivity
import com.example.assignment.core.MyApplication
import com.example.assignment.exchangeSymbols.adapters.ExchangeSymbolAdapter
import com.example.assignment.exchangeSymbols.presenter.ExchangeSymbolPresenter
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ExchangeSymbolActivity : BaseActivity(), ExchangeSymbolView {

    private val component by lazy {
        (applicationContext as MyApplication).appComponent
            .requestExchangeSymbolComponentBuilder()
            .build()
    }

    @ProvidePresenter
    fun providePresenter(): ExchangeSymbolPresenter = component.presenter()

    @InjectPresenter
    lateinit var exchangeSymbolPresenter: ExchangeSymbolPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_symbol)
    }

    override fun onResume() {
        super.onResume()

        exchangeSymbolPresenter.getExchangeSymbols()
    }

    override fun setUpRecyclerView(exchangeRatesModel: Array<String>) {
        val viewAdapter = ExchangeSymbolAdapter(exchangeRatesModel)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }

        recyclerView.adapter?.notifyDataSetChanged()
    }
}