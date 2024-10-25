package com.example.currencyconverter.repository

import com.example.currencyconverter.network.CurrencyApi
import com.example.currencyconverter.data.models.CurrencyResponse
import com.example.currencyconverter.util.Resource
import javax.inject.Inject

// Repository implementation for fetching currency rates
class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepository {

    override suspend fun getRate(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base = base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error has occurred")
        }
    }
}