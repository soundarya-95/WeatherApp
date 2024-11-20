package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherByCoordinates(lat: Double, lon: Double, apiKey: String): WeatherInfo
}