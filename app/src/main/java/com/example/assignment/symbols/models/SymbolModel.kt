package com.example.assignment.symbols.models

import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Single

interface SymbolModel {
    fun downloadSymbols(): Single<SymbolsMap>
}