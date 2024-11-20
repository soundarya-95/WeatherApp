package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.domain.model.WeatherInfo
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {

    private val weatherApi: WeatherApi = mockk()
    private val weatherResponseMapper: WeatherResponseMapper = mockk()
    private lateinit var weatherRepositoryImpl: WeatherRepositoryImpl
    private val apiKey = "testApiKey"

    @Before
    fun setUp() {
        weatherRepositoryImpl = WeatherRepositoryImpl(weatherApi, weatherResponseMapper)
    }

    @After
    fun tearDown() {
        clearMocks(weatherApi, weatherResponseMapper)
    }

    @Test
    fun `getWeatherByCoordinates should return WeatherInfo when API call is successful`() = runTest {
        // Arrange
        val lat = 40.7128
        val lon = -74.0060
        val response = mockk<WeatherResponse>() // Replace with the actual response type from WeatherApi
        val weatherInfo = WeatherInfo(
            city = "New York",
            temperature = 25.0,
            humidity = 60,
            description = "Clear sky",
            icon = "01d",
            feelsLike = 23.0
        )

        // Mock the WeatherApi response
        coEvery { weatherApi.getWeatherByCoordinates(lat, lon, apiKey) } returns response
        // Mock the WeatherResponseMapper to map the response to WeatherInfo
        every { weatherResponseMapper.mapWeatherResponse(response) } returns weatherInfo

        // Act
        val result = weatherRepositoryImpl.getWeatherByCoordinates(lat, lon, apiKey)

        // Assert
        assertEquals(weatherInfo, result)
        coVerify { weatherApi.getWeatherByCoordinates(lat, lon, apiKey) }
        verify { weatherResponseMapper.mapWeatherResponse(response) }
    }

    @Test
    fun `getWeatherByCoordinates should throw exception when API call fails`() = runTest {
        // Arrange
        val lat = 40.7128
        val lon = -74.0060
        val exceptionMessage = "API request failed"

        // Mock the WeatherApi to throw an exception
        coEvery { weatherApi.getWeatherByCoordinates(lat, lon, apiKey) } throws RuntimeException(exceptionMessage)

        // Act & Assert
        try {
            weatherRepositoryImpl.getWeatherByCoordinates(lat, lon, apiKey)
        } catch (e: Exception) {
            assertEquals(exceptionMessage, e.message)
        }

        coVerify { weatherApi.getWeatherByCoordinates(lat, lon, apiKey) }
    }
}
