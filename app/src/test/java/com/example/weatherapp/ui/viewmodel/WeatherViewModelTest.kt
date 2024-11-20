package com.example.weatherapp.ui.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.domain.model.GeoLocationInfo
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.usecase.GeoLocationUseCase
import com.example.weatherapp.domain.usecase.WeatherUseCase
import com.example.weatherapp.ui.view.CityStorage
import com.example.weatherapp.ui.viewmodel.WeatherUIState
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val weatherUseCase: WeatherUseCase = mockk()
    private val geoLocationUseCase: GeoLocationUseCase = mockk()
    private val cityStorage: CityStorage = mockk()
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        every { cityStorage.getCity() } returns ""
        viewModel = WeatherViewModel(
            weatherUseCase = weatherUseCase,
            geoLocationUseCase = geoLocationUseCase,
            dispatcher = dispatcher,
            cityStorage = cityStorage
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchWeatherByCity should update uiState with weather data on success`() = runTest {
        val city = "London"
        val geoLocationInfo = GeoLocationInfo(lat = 51.5074, lon = -0.1278)
        val weatherInfo = WeatherInfo(
            city = "London",
            temperature = 15.0,
            humidity = 70,
            description = "Cloudy",
            icon = "cloudy",
            feelsLike = 14.5
        )

        coEvery { geoLocationUseCase.getGeoLocationForCity(city) } returns geoLocationInfo
        coEvery { weatherUseCase.getWeatherByCoordinates(geoLocationInfo.lat, geoLocationInfo.lon) } returns weatherInfo
        every { cityStorage.getCity() } returns ""

        viewModel.fetchWeatherByCity(city)
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertEquals("London", uiState.city)
        assertEquals("15.0°C", uiState.temperature)
        assertEquals("70%", uiState.humidity)
        assertEquals("Cloudy", uiState.description)
        assertEquals(
            "https://openweathermap.org/img/wn/cloudy@4x.png",
            uiState.iconUrl
        )
        assertEquals(false, uiState.isLoading)
        assertNull(uiState.errorMessage)
    }

    @Test
    fun `fetchWeatherByCity should update uiState with error message on failure`() = runTest {
        val city = "InvalidCity"
        val errorMessage = "City not found"
        coEvery { geoLocationUseCase.getGeoLocationForCity(city) } throws RuntimeException(errorMessage)
        every { cityStorage.getCity() } returns ""
        // Act
        viewModel.fetchWeatherByCity(city)
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertEquals(errorMessage, uiState.errorMessage)
        assertEquals(false, uiState.isLoading)
    }

    @Test
    fun `fetchWeatherByCoordinates should update uiState with weather data on success`() = runTest {
        val latitude = 40.7128
        val longitude = -74.0060
        val weatherInfo = WeatherInfo(
            city = "New York",
            temperature = 20.0,
            humidity = 60,
            description = "Sunny",
            icon = "sunny",
            feelsLike = 19.0
        )

        coEvery { weatherUseCase.getWeatherByCoordinates(latitude, longitude) } returns weatherInfo
        every { cityStorage.getCity() } returns ""

        viewModel.fetchWeatherByCoordinates(latitude, longitude)
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertEquals("New York", uiState.city)
        assertEquals("20.0°C", uiState.temperature)
        assertEquals("60%", uiState.humidity)
        assertEquals("Sunny", uiState.description)
        assertEquals(
            "https://openweathermap.org/img/wn/sunny@2x.png",
            uiState.iconUrl
        )
        assertEquals(" 19.0°C", uiState.feelsLike)
        assertEquals(false, uiState.isLoading)
        assertNull(uiState.errorMessage)
    }

    @Test
    fun `fetchWeatherByCoordinates should update uiState with error message on failure`() = runTest {
        val latitude = 0.0
        val longitude = 0.0
        val errorMessage = "Unable to fetch weather data"
        coEvery { weatherUseCase.getWeatherByCoordinates(latitude, longitude) } throws RuntimeException(errorMessage)
        every { cityStorage.getCity() } returns ""

        viewModel.fetchWeatherByCoordinates(latitude, longitude)
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertEquals(errorMessage, uiState.errorMessage)
        assertEquals(false, uiState.isLoading)
    }
}
