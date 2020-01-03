package com.example.assignment.symbols.models

import com.example.assignment.symbols.data.SymbolsMap
import io.reactivex.Observable

interface SymbolModel {
    fun downloadSymbols(): Observable<SymbolsMap>
}