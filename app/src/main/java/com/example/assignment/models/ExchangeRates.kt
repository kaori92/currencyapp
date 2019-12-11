package com.example.assignment.models

import com.google.gson.annotations.SerializedName
import java.util.logging.Logger

data class ExchangeRates(
    @SerializedName("timestamp") var timestamp: Int,
    @SerializedName("date") var date: String,
    @SerializedName("base") var base: String,
    @SerializedName("rates") var rates: Map<String, Double>
) {

    fun getExchangeRatesTexts(): Array<String> {
        var exchangeRates = Array(rates.size + 1) { "" }
        exchangeRates[0] = "$base 1"
        var i = 1
        for ((currency, value) in rates.entries) {
            exchangeRates[i] = "$currency $value"
            i++
        }

        Logger.getLogger(ExchangeRates::class.java.name).warning("exchangeRates $exchangeRates")
        return exchangeRates
    }
}