package com.example.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.repository.MainRepository
import com.example.currencyconverter.util.DispatcherProvider
import com.example.currencyconverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    // Sealed class representing different states of currency conversion events
    sealed class CurrencyEvent {
        class Success(val resultText: String) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    // MutableStateFlow to hold the current state of currency conversion
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    // Publicly exposed StateFlow to observe the conversion state
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when (val ratesResponse = repository.getRate("EUR")) {
                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Failure(ratesResponse.message!!)

                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val fromRate = rates[fromCurrency]
                    val toRate = rates[toCurrency]
                    // Check if rates are available for the given currencies
                    if (fromRate == null || toRate == null) {
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        // Calculate the converted currency amount
                        val convertedCurrency = round((fromAmount / fromRate) * toRate * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }
}