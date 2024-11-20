package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.usecase.GeoLocationUseCase
import com.example.weatherapp.domain.usecase.WeatherUseCase
import com.example.weatherapp.ui.view.CityStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val geoLocationUseCase: GeoLocationUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val cityStorage: CityStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUIState())

    val uiState: StateFlow<WeatherUIState> = _uiState

    init {
        cityStorage.getCity().takeIf { it.isNotEmpty() }?.let {
            fetchWeatherByCity(it)
        }
    }

    fun fetchWeatherByCity(city: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch(dispatcher) {
            try {
                // fetch the geo location first
                val geoLocation = geoLocationUseCase.getGeoLocationForCity(city)

                // fetch the weather using the coordinates returned in the geoLocation
                val weather =
                    weatherUseCase.getWeatherByCoordinates(geoLocation.lat, geoLocation.lon)
                _uiState.value = WeatherUIState(
                    city = weather.city,
                    temperature = "${weather.temperature}°C",
                    humidity = "${weather.humidity}%",
                    description = weather.description,
                    iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@4x.png",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch(dispatcher) {
            try {
                val weather = weatherUseCase.getWeatherByCoordinates(lat, lon)
                _uiState.value = WeatherUIState(
                    city = weather.city,
                    temperature = "${weather.temperature}°C",
                    humidity = "${weather.humidity}%",
                    description = weather.description,
                    iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@2x.png",
                    isLoading = false,
                    feelsLike = " ${weather.feelsLike}°C"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}

data class WeatherUIState(
    val city: String = "",
    val temperature: String = "",
    val humidity: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val feelsLike: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)