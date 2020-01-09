package com.example.assignment.symbols.data

import com.google.gson.annotations.SerializedName

data class SymbolsMap(
    @SerializedName("symbols") val map: Map<String, String>
)