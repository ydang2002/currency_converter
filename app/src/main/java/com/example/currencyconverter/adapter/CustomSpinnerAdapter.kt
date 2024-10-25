package com.example.currencyconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.currencyconverter.databinding.SpinnerItemBinding

class CustomSpinnerAdapter(context: Context, private val currencyList: List<CurrencyItem>) :
    ArrayAdapter<CurrencyItem>(context, 0, currencyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    // Creates or reuses a view for a currency item at the specified position
    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: SpinnerItemBinding
        if (convertView == null) {
            binding = SpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            binding = SpinnerItemBinding.bind(convertView)
        }

        val currencyItem = getItem(position)
        currencyItem?.let {
            binding.ivFlag.setImageResource(it.flagResId)
            binding.tvCurrencyCode.text = it.currencyCode
        }

        return binding.root
    }
}

data class CurrencyItem(val currencyCode: String, val flagResId: Int)

