package com.example.assignment.exchange.data

import com.google.gson.annotations.SerializedName

data class ExchangeRates(
    @SerializedName("timestamp") val timestamp: Int,
    @SerializedName("date") val date: String,
    @SerializedName("base") val base: String,
    @SerializedName("rates") val rates: Map<String, Double>
){
    fun getExchangeRatesTexts(): Array<String> {
        return rates.entries.map { (currency, value) -> "$currency $value" }.toTypedArray()
    }

    fun getArray(): Array<String> {
        return rates.entries.map {(currency, _) -> currency}.toTypedArray()
    }
}
