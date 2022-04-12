package com.app.ashikcarnottest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.app.ashikcarnottest.apiservice.NetworkStateListener

class AppUtils {

    companion object {
        fun isNetworkAvailable(context: Context) {
            try {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val builder = NetworkRequest.Builder()
                builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                val networkRequest = builder.build()
                connectivityManager.registerNetworkCallback(networkRequest,
                    object : ConnectivityManager.NetworkCallback () {
                        override fun onAvailable(network: Network) {
                            super.onAvailable(network)
                            Log.i("Test", "Network Available")
                            (context as NetworkStateListener).networkState(true)
                        }
                        override fun onLost(network: Network) {
                            super.onLost(network)
                            Log.i("Test", "Connection lost")
                            (context as NetworkStateListener).networkState(false)
                        }
                    })
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun showToast(context: Context, message: String?) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }
}