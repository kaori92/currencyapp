package com.example.assignment.symbols.data

import com.google.gson.annotations.SerializedName

data class SymbolsList (
    @SerializedName("symbols") val text: String
){
    fun getList(): List<String> = text.split(",").map { it.trim() }
}