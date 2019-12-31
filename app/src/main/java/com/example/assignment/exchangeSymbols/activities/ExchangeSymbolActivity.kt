package com.example.assignment.exchangeSymbols.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.MyApplicationComponent
import com.example.assignment.R
import com.example.assignment.exchangeSymbols.adapters.ExchangeSymbolAdapter
import com.example.assignment.exchangeSymbols.presenter.ExchangeSymbolPresenter
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolView
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ExchangeSymbolActivity: MvpAppCompatActivity(), ExchangeSymbolView {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val viewManager = LinearLayoutManager(this)

    private val component by lazy {
        (applicationContext as MyApplicationComponent)
            .appComponent.requestExchangeSymbolComponentBuilder().build()
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
        viewAdapter =
            ExchangeSymbolAdapter(exchangeRatesModel)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun showErrorToast(error: Throwable) {
        showError(error, this)
    }
}