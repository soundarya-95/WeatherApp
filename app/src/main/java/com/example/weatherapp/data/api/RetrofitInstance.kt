package com.example.weatherapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Converter
import javax.inject.Inject

class RetrofitInstance @Inject constructor(
    private val converterFactory: Converter.Factory,
    private val okHttpClient: OkHttpClient
) {

    private val baseUrl = "https://api.openweathermap.org/"

    fun <T> createApi(apiClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(apiClass)
    }
}
