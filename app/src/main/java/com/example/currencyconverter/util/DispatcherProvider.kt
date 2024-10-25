package com.example.currencyconverter.util

import kotlinx.coroutines.CoroutineDispatcher

// Interface providing different CoroutineDispatchers for various types of tasks
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}