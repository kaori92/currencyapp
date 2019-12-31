package com.example.assignment.exchangeSymbols.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R

class ExchangeSymbolAdapter(private val exchangeSymbols: Array<String>):
RecyclerView.Adapter<ExchangeSymbolAdapter.ViewHolder>() {

    private var position: Int? = null
    private var rateSymbols: Array<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerTextView?.text = exchangeSymbols[position]
        this.position = position
        this.rateSymbols = exchangeSymbols
    }

    override fun getItemCount() = exchangeSymbols.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerTextView = itemView.findViewById<TextView>(R.id.recycler_text_view)
    }
}