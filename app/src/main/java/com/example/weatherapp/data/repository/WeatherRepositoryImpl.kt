package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherResponseMapper: WeatherResponseMapper
) : WeatherRepository {

    override suspend fun getWeatherByCoordinates(
        lat: Double,
        lon: Double,
        apiKey: String
    ): WeatherInfo {
        val response = weatherApi.getWeatherByCoordinates(lat, lon, apiKey)
        return weatherResponseMapper.mapWeatherResponse(response)
    }
}