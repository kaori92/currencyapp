package com.example.assignment.symbols.models

import com.example.assignment.BuildConfig
import com.example.assignment.retrofit.CurrencyRetrofitService
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable

data class SymbolModel(
    private val apiService: CurrencyRetrofitService
) {
    fun downloadSymbols(): Observable<SymbolsMap> {
        return apiService.getSymbols(BuildConfig.API_KEY)
    }
}