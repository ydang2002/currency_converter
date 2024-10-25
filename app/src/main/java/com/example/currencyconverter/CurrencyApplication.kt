package com.example.currencyconverter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Annotation to trigger Hilt's code generation, including a base class for the application
@HiltAndroidApp
class CurrencyApplication : Application()