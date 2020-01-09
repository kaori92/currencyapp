package com.example.assignment.symbols.models

import com.example.assignment.api.CurrencyRetrofitService
import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Single

class DefaultSymbolModel(
    private val apiService: CurrencyRetrofitService
) : SymbolModel {

    override fun downloadSymbols(): Single<SymbolsMap> {
        return apiService.getSymbols()
    }

}