package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.domain.model.WeatherInfo
import javax.inject.Inject

class WeatherResponseMapper @Inject constructor() {

    fun mapWeatherResponse(weatherResponse: WeatherResponse): WeatherInfo {
        with(weatherResponse) {
            return WeatherInfo(
                city = name,
                description = weather.firstOrNull()?.description ?: "No description",
                feelsLike = main.feelsLike,
                icon = weather.firstOrNull()?.icon.orEmpty(),
                humidity = main.humidity,
                temperature = main.temp
            )
        }
    }
}