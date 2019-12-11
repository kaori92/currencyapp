package com.example.assignment

import com.example.assignment.models.ExchangeRates

interface ExchangeRateCallback {
    fun onSuccess(value: ExchangeRates)
    fun onError()
}