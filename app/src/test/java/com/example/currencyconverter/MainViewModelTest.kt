package com.example.currencyconverter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyconverter.data.models.CurrencyResponse
import com.example.currencyconverter.repository.MainRepository
import com.example.currencyconverter.util.DispatcherProvider
import com.example.currencyconverter.util.Resource
import com.example.currencyconverter.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: MainRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Sử dụng MockK để tạo một mock của repository
        repository = mockk()
        Dispatchers.setMain(testDispatcher)

        val dispatchers = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }

        viewModel = MainViewModel(repository, dispatchers)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()  // Restore main dispatcher after each test
    }

    @Test
    fun `convert valid amount returns success`() = runTest {
        val rates = mapOf("USD" to 1.0, "EUR" to 0.85)
        val response = CurrencyResponse(
            base = "EUR",
            date = "2023-10-25",
            rates = rates,
            success = true,
            timestamp = 1234567890
        )

        // Simulate successful results from repository
        coEvery { repository.getRate("EUR") } returns Resource.Success(response)
        // Call convert function and wait for coroutine to complete
        viewModel.convert("100.0", "USD", "EUR")
        advanceUntilIdle()

        // Get the result value and check
        val result = viewModel.conversion.value
        // Check if the result is an instance of CurrencyEvent.Success
        assertTrue(result is MainViewModel.CurrencyEvent.Success)

        // Check resultText, combine trim() to avoid errors due to whitespace
        val expectedText = "100.0 USD = 85.0 EUR"
        val actualText = (result as MainViewModel.CurrencyEvent.Success).resultText.trim()

        assertTrue(expectedText == actualText)
    }

    @Test
    fun `convert invalid amount returns failure`() = runTest {
        viewModel.convert("invalid", "USD", "EUR")

        val result = viewModel.conversion.value
        assert(result is MainViewModel.CurrencyEvent.Failure)
        assert((result as MainViewModel.CurrencyEvent.Failure).errorText == "Not a valid amount")
    }

    @Test
    fun `api error returns failure`() = runTest {
        // Simulate the case where getRate returns Resource.Error
        coEvery { repository.getRate("EUR") } returns Resource.Error("API error")

        viewModel.convert("100.0", "USD", "EUR")
        advanceUntilIdle()

        val result = viewModel.conversion.value
        assertTrue(result is MainViewModel.CurrencyEvent.Failure)
        assertTrue((result as MainViewModel.CurrencyEvent.Failure).errorText.trim() == "API error")
    }
}