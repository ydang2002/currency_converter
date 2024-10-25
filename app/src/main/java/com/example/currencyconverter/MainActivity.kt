package com.example.currencyconverter

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverter.data.CurrencyData
import com.example.currencyconverter.network.NetworkUtils.isNetworkAvailable
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.adapter.CurrencyItem
import com.example.currencyconverter.adapter.CustomSpinnerAdapter
import com.example.currencyconverter.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currencyList = CurrencyData.currencyList

        val adapter = CustomSpinnerAdapter(this, currencyList)
        binding.spFromCurrency.adapter = adapter
        binding.spToCurrency.adapter = adapter

        binding.btnConvert.setOnClickListener {
            if (!isNetworkAvailable(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fromCurrencyItem = binding.spFromCurrency.selectedItem as CurrencyItem
            val toCurrencyItem = binding.spToCurrency.selectedItem as CurrencyItem

            viewModel.convert(
                binding.etFrom.text.toString(),
                fromCurrencyItem.currencyCode,
                toCurrencyItem.currencyCode
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.resultText
                    }

                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText
                    }

                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    else -> Unit
                }
            }
        }
    }
}