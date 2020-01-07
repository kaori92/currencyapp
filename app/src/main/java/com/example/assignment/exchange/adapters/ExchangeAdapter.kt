package com.example.assignment.exchange.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.exchange.data.ExchangeRates

class ExchangeAdapter(
    private val exchangeRates: ExchangeRates,
    private val itemClickListener: ExchangeRatesItemClickListener
) : RecyclerView.Adapter<ExchangeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerTextView.text = exchangeRates.exchangeRatesTexts[position]

        holder.itemView.setOnClickListener{
            val base = exchangeRates.currencies[position]
            itemClickListener.onClick(base)
        }
    }

    override fun getItemCount() = exchangeRates.rates.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerTextView: TextView = itemView.findViewById(R.id.recycler_text_view)
    }

    interface ExchangeRatesItemClickListener {
        fun onClick(base: String)
    }
}