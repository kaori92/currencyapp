package com.example.assignment.exchange.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.MyApplicationComponent
import com.example.assignment.R
import com.example.assignment.exchange.adapters.ExchangeAdapter
import com.example.assignment.exchange.data.ExchangeRates
import com.example.assignment.exchange.presenter.ExchangePresenter
import com.example.assignment.exchange.view.ExchangeView
import com.example.assignment.symbols.activities.SymbolActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ExchangeActivity : MvpAppCompatActivity(), ExchangeView {
    override fun showErrorToast(error: Throwable) {
        showError(error, this)
    }

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val viewManager = LinearLayoutManager(this)
    private var disposable: Disposable? = null
    private val date = "2013-12-24"

    private val component by lazy {
        (applicationContext as MyApplicationComponent)
            .appComponent.requestExchangeComponentBuilder().build()
    }

    @ProvidePresenter
    fun providePresenter(): ExchangePresenter = component
        .presenter()

    @InjectPresenter
    lateinit var exchangePresenter: ExchangePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

//        exchangePresenter.getExchangeRates()
//        exchangePresenter.getRatesForDate(date)
//        disposable = exchangePresenter.getExchangeRatesPeriodically()
    }

    private val itemOnClick: (ExchangeRates, Int) -> Unit = { exchangeRates, position ->
        val intent = Intent(this, SymbolActivity::class.java)
        intent.putExtra("base", exchangeRates.getArray()[position])
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        disposable?.let {
            if(!it.isDisposed){
                it.dispose()
            }
        }
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
}