package com.example.assignment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.models.ExchangeRates
import com.example.assignment.retrofit.ApiClient
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var progressDialog: ProgressDialog
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun setUpRecyclerView(exchangeRates: ExchangeRates) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(exchangeRates)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun getData() {
        // TODO move to another class, put in bckground thread
        val requestInterface = ApiClient.getClient

        requestInterface.getExchangeRates(BuildConfig.API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(exchangeRates: ExchangeRates) {
        progressDialog.dismiss()
        Logger.getLogger(MainActivity::class.java.name).warning("body: $exchangeRates")
        setUpRecyclerView(exchangeRates)

        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun handleError(error: Throwable) {
        progressDialog.dismiss()
        Logger.getLogger(MainActivity::class.java.name).warning("onFailure ${error?.message}")
    }
}


