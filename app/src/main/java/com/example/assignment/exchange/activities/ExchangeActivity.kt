package com.example.assignment.exchange.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.*
import com.example.assignment.exchange.adapters.ExchangeAdapter
import com.example.assignment.MyApplicationComponent
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.presenter.ExchangePresenter
import com.example.assignment.exchange.view.ExchangeView
import com.example.assignment.symbols.activities.SymbolActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ExchangeActivity : MvpAppCompatActivity(), ExchangeView {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val viewManager = LinearLayoutManager(this)

    val itemOnClick: (ExchangeRates, Int) -> Unit = { exchangeRates, position ->
        val intent = Intent(this, SymbolActivity::class.java)
        intent.putExtra("base", exchangeRates.getArray()[position])
        startActivity(intent)
    }

    @ProvidePresenter
    fun providePresenter(): ExchangePresenter = component
        .presenter()

    @InjectPresenter
    lateinit var exchangePresenter: ExchangePresenter

    private val component by lazy {
        (applicationContext as MyApplicationComponent)
            .appComponent.requestExchangeComponentBuilder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exchangePresenter.getExchangeRates()
    }


    override fun setUpRecyclerView(exchangeRatesModel: ExchangeRates) {
        viewAdapter =
            ExchangeAdapter(exchangeRatesModel, itemOnClick)

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