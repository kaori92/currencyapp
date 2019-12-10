package com.example.assignment.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    @Expose
    @SerializedName("timestamp")
    val timestamp: Integer,
    @Expose
    @SerializedName("date")
    val date: String,
    @Expose
    @SerializedName("base")
    val base: String,
    @Expose
    @SerializedName("rates")
    val rates: Array<Double>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExchangeRate

        if (!rates.contentEquals(other.rates)) return false

        return true
    }

    override fun hashCode(): Int {
        return rates.contentHashCode()
    }
}