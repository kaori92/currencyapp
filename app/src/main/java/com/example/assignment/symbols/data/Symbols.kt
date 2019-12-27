package com.example.assignment.symbols.data

import com.google.gson.annotations.SerializedName

data class Symbols(
    @SerializedName("symbols") val map: Map<String, String>
)