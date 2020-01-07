package com.example.assignment.symbols.models

import com.example.assignment.retrofit.CurrencyRetrofitService
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable

class DefaultSymbolModel(
    private val apiService: CurrencyRetrofitService
) : SymbolModel {

    override fun downloadSymbols(): Observable<SymbolsMap> {
        return apiService.getSymbols()
    }

}