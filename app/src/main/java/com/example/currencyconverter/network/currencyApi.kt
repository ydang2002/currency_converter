package com.example.currencyconverter.network

import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest")
    suspend fun getRates(
        @Query("access_key") accessKey: String = BuildConfig.API_KEY,
        @Query("base") base: String
    ): Response<CurrencyResponse>
}