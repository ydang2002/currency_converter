package com.example.currencyconverter

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.example.currencyconverter.network.NetworkUtils
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class NetworkUtilsTest {

    @Test
    fun `network available returns true`() {
        // Create mocks for Context, ConnectivityManager, NetworkCapabilities and Network
        val context = mockk<Context>()
        val connectivityManager = mockk<ConnectivityManager>()
        val networkCapabilities = mockk<NetworkCapabilities>()
        val network = mockk<Network>()

        // Simulate getSystemService, activeNetwork, getNetworkCapabilities, hasTransport methods
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns network
        every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true

        // Call the isNetworkAvailable function and check if the result returns true
        val result = NetworkUtils.isNetworkAvailable(context)
        assert(result)
    }

    @Test
    fun `network not available returns false`() {
        // Call the isNetworkAvailable function and check if the result returns true
        val context = mockk<Context>()
        val connectivityManager = mockk<ConnectivityManager>()

        // Simulate getSystemService, activeNetwork,
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns null

        // Call the isNetworkAvailable function and check if the result returns false
        val result = NetworkUtils.isNetworkAvailable(context)
        assert(!result)
    }
}