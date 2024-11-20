package com.example.weatherapp.domain.model

data class WeatherInfo(
    val city: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val description: String,
    val icon:String
)
