package com.example.weatherapp.data.model

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "name") val name: String,
    @Json(name = "weather") val weather: List<Weather>,
    @Json(name = "main") val main: Main
)

data class Weather(
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)

data class Main(
    @Json(name = "temp") val temp: Double,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "feels_like") val feelsLike:Double
)
