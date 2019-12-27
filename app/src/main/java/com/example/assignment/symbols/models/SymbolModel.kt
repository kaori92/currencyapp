package com.example.assignment.symbols.models

import com.example.assignment.BuildConfig
import com.example.assignment.retrofit.CurrencyRetrofitService
import com.example.assignment.symbols.data.Symbols
import io.reactivex.Observable
import io.reactivex.Single

data class SymbolModel(
    private val apiService: CurrencyRetrofitService
) {
    fun downloadSymbols(): Observable<Symbols> {
        return apiService.getSymbols(BuildConfig.API_KEY)
    }
}