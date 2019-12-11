package com.example.assignment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.models.ExchangeRates
import com.example.assignment.retrofit.ApiClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var progressDialog: ProgressDialog
    private lateinit var exchangeRates: ExchangeRates

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
        val call: Call<ExchangeRates> = ApiClient.getClient.getExchangeRates(BuildConfig.API_KEY)
        call.enqueue(object : Callback<ExchangeRates> {

            override fun onResponse(
                call: Call<ExchangeRates>?,
                response: Response<ExchangeRates>?
            ) {
                progressDialog.dismiss()
                Logger.getLogger(MainActivity::class.java.name).warning("response : $response")

                if (response?.isSuccessful()!!) {
                    response.body()?.let {
                        Logger.getLogger(MainActivity::class.java.name).warning("body: $it")
                        exchangeRates = it
                        setUpRecyclerView(it)
                    }
                }

                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ExchangeRates>?, t: Throwable?) {
                progressDialog.dismiss()
                Logger.getLogger(MainActivity::class.java.name).warning("onFailure ${t?.message}")
            }
        })
    }
}


