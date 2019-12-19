package com.example.assignment.exchange.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.exchange.data.data.ExchangeRates

class RecyclerViewAdapter(private val myDataset: ExchangeRates) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerTextView?.text = myDataset.getExchangeRatesTexts()[position]
    }

    override fun getItemCount() = myDataset.getExchangeRatesTexts().size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerTextView = itemView.findViewById<TextView>(R.id.recycler_text_view)
    }
}
