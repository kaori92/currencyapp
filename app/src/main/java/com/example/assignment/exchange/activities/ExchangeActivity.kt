package com.example.assignment.exchange.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.*
import com.example.assignment.exchange.adapters.RecyclerViewAdapter
import com.example.assignment.exchange.components.ExchangeComponent
import com.example.assignment.exchange.components.MyApplicationComponent
import com.example.assignment.exchange.data.data.ExchangeRates
import com.example.assignment.exchange.presenter.ExchangePresenter
import com.example.assignment.exchange.view.ExchangeView
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class ExchangeActivity : MvpAppCompatActivity(), ExchangeView {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val viewManager = LinearLayoutManager(this)

    @ProvidePresenter
    fun providePresenter() = component
        .presenter()

    @InjectPresenter
    lateinit var exchangePresenter: ExchangePresenter

    private val component by lazy {
        (applicationContext as MyApplicationComponent)
            .appComponent.requestExchangeComponentBuilder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exchangePresenter.getExchangeRates()
    }

    override fun setUpRecyclerView(exchangeRatesModel: ExchangeRates) {
        viewAdapter =
            RecyclerViewAdapter(exchangeRatesModel)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.adapter?.notifyDataSetChanged()
    }
}