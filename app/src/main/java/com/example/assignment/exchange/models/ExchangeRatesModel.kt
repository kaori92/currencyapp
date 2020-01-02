package com.example.assignment.exchange.models

import com.example.assignment.exchange.data.ExchangeRates
import io.reactivex.Maybe
import io.reactivex.Observable

interface ExchangeRatesModel{
    fun downloadExchangeRates(): Observable<ExchangeRates>

    fun downloadRatesForDate(date: String): Maybe<ExchangeRates>
}