package com.example.assignment.exchange.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.exchange.data.ExchangeRates

class ExchangeAdapter(private val exchangeRates: ExchangeRates,
                      private val itemClickListener: (ExchangeRates, Int) -> Unit) :
    RecyclerView.Adapter<ExchangeAdapter.ViewHolder>() {
    private var position: Int? = null
    private var rates: ExchangeRates? = null

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        val viewHolder = ViewHolder(textView)
        viewHolder.onClick(itemClickListener)
        return viewHolder
    }

    private fun <T : RecyclerView.ViewHolder> T.onClick(
        event: (exchangeRates: ExchangeRates, position: Int) -> Unit): T
    {
        itemView.setOnClickListener {
            event.invoke(exchangeRates, position)
        }
        return this
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerTextView?.text = exchangeRates.getExchangeRatesTexts()[position]
        this.position = position
        this.rates = exchangeRates
    }

    override fun getItemCount() = exchangeRates.getExchangeRatesTexts().size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerTextView = itemView.findViewById<TextView>(R.id.recycler_text_view)
    }
}
