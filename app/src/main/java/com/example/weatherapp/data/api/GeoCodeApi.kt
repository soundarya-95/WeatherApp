package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.GeoLocation
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeApi {
    @GET("geo/1.0/direct")
    suspend fun getGeoCode(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): List<GeoLocation>
}