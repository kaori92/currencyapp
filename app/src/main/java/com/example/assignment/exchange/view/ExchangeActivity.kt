package com.example.assignment.exchange.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.core.BaseActivity
import com.example.assignment.core.MyApplication
import com.example.assignment.core.TIMER_PERIOD
import com.example.assignment.exchange.adapters.ExchangeAdapter
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.presenter.ExchangePresenter
import com.example.assignment.exchangeSymbols.view.ExchangeSymbolActivity
import com.example.assignment.posts.view.PostActivity
import com.example.assignment.symbols.view.SymbolActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ExchangeActivity : BaseActivity(), ExchangeView {

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

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

        setSupportActionBar(findViewById(R.id.my_toolbar))
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_exchange_symbols -> handleExchangeSymbolSelected()
        R.id.action_exchange_timer -> handleExchangeTimerSelected()
        R.id.action_exchange_date -> handleExchangeDateSelected()
        R.id.action_delete -> handleDeleteSelected()

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun handleDeleteSelected(): Boolean {
        exchangePresenter.diposeOfTimer()
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun handleExchangeDateSelected(): Boolean {
        exchangePresenter.diposeOfTimer()
        showToast("Showing rates for date 2013-12-24")
        exchangePresenter.getRatesForDate("2013-12-24")
        return true
    }

    private fun handleExchangeTimerSelected(): Boolean {
        showToast("Showing rates every $TIMER_PERIOD seconds")
        exchangePresenter.getExchangeRatesPeriodically(this)
        return true
    }

    private fun handleExchangeSymbolSelected(): Boolean {
        exchangePresenter.diposeOfTimer()
        showToast("Going to exchange and symbols activity")
        val intent = Intent(this, ExchangeSymbolActivity::class.java)
        startActivity(intent)
        return true
    }

    override fun onPause() {
        super.onPause()
        exchangePresenter.diposeOfTimer()
    }

}