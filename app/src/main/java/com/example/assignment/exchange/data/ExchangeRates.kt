package com.example.assignment.exchange.data

import com.google.gson.annotations.SerializedName

data class ExchangeRates(
    @SerializedName("timestamp") val timestamp: Int,
    @SerializedName("date") val date: String,
    @SerializedName("base") val base: String,
    @SerializedName("rates") val rates: Map<String, Double>
) {
    val currencies: List<String>
        get() = rates.keys.toList()

    val exchangeRatesTexts: List<String>
        get() = rates.entries.map { (currency, value) -> "$currency $value" }.toList()
}
