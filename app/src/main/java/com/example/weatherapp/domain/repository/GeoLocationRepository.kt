package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.GeoLocationInfo

interface GeoLocationRepository {
    suspend fun getGeoLocationForCity(city: String, apiKey: String): GeoLocationInfo
}