package com.example.assignment.exchange.view

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.core.BaseActivity
import com.example.assignment.core.MyApplication
import com.example.assignment.exchange.adapters.ExchangeAdapter
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.presenter.ExchangePresenter
import com.example.assignment.symbols.view.SymbolActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ExchangeActivity : BaseActivity(), ExchangeView {

    private val component by lazy {
        (applicationContext as MyApplication).appComponent
            .requestExchangeComponentBuilder()
            .build()
    }

    @ProvidePresenter
    fun providePresenter(): ExchangePresenter = component.presenter()

    @InjectPresenter
    lateinit var exchangePresenter: ExchangePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        exchangePresenter.getExchangeRates()
    }

    private fun onItemClick(base: String) {
        val intent = Intent(this, SymbolActivity::class.java)
        intent.putExtra("base", base)
        startActivity(intent)
    }

    override fun setUpRecyclerView(exchangeRatesModel: ExchangeRates) {
        val viewAdapter = ExchangeAdapter(
            exchangeRatesModel,
            object : ExchangeAdapter.ExchangeRatesItemClickListener {
                override fun onClick(base: String) {
                    onItemClick(base)
                }
            }
        )

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }

        recyclerView.adapter?.notifyDataSetChanged()
    }
}