package com.example.currencyconverter.repository

import com.example.currencyconverter.data.models.CurrencyResponse
import com.example.currencyconverter.util.Resource

// Interface defining the contract for the main repository
interface MainRepository {
    suspend fun getRate(base: String): Resource<CurrencyResponse>
}