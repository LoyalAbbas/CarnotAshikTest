package com.app.ashikcarnottest.apiservice

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    companion object {
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit? {
            val interceptor = HttpLoggingInterceptor()
            var client: OkHttpClient

            interceptor.level = HttpLoggingInterceptor.Level.BODY
                client = OkHttpClient.Builder().addInterceptor(interceptor)
                    .connectTimeout(30000, TimeUnit.MILLISECONDS).build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit
        }

        fun getBaseUrl(): String {
            return "https://api.data.gov.in"
        }

    }
}