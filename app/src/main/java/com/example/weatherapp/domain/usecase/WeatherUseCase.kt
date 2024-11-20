package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Named

class WeatherUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository,
   @Named("weatherApiKey") private val apiKey: String
) : WeatherUseCase {

    override suspend fun getWeatherByCoordinates(lat: Double, lon: Double) =
        repository.getWeatherByCoordinates(lat, lon, apiKey)
}

interface WeatherUseCase {
    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): WeatherInfo
}