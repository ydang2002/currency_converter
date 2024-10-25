package com.example.currencyconverter.data.models

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Float>,
    val success: Boolean,
    val timestamp: Int
)