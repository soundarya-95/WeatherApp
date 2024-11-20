package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.WeatherUseCaseImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherUseCaseImplTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var weatherUseCaseImpl: WeatherUseCaseImpl
    private val apiKey = "testApiKey"

    @Before
    fun setUp() {
        weatherUseCaseImpl = WeatherUseCaseImpl(repository, apiKey)
    }

    @After
    fun tearDown() {
        clearMocks(repository)
    }

    @Test
    fun `getWeatherByCoordinates should return weather info when repository call is successful`() = runTest {
        // Arrange
        val lat = 40.7128
        val lon = -74.0060
        val weatherInfo = WeatherInfo(
            city = "New York",
            temperature = 22.0,
            humidity = 60,
            description = "Clear Sky",
            icon = "01d",
            feelsLike = 20.0
        )

        // Mock the repository to return the weather info
        coEvery { repository.getWeatherByCoordinates(lat, lon, apiKey) } returns weatherInfo

        // Act
        val result = weatherUseCaseImpl.getWeatherByCoordinates(lat, lon)

        // Assert
        assertEquals(weatherInfo, result)
        coVerify { repository.getWeatherByCoordinates(lat, lon, apiKey) }
    }

    @Test
    fun `getWeatherByCoordinates should throw an exception when repository call fails`() = runTest {
        // Arrange
        val lat = 40.7128
        val lon = -74.0060
        val exceptionMessage = "Failed to fetch weather"
        coEvery { repository.getWeatherByCoordinates(lat, lon, apiKey) } throws RuntimeException(exceptionMessage)

        // Act & Assert
        try {
            weatherUseCaseImpl.getWeatherByCoordinates(lat, lon)
        } catch (e: Exception) {
            assertEquals(exceptionMessage, e.message)
        }

        coVerify { repository.getWeatherByCoordinates(lat, lon, apiKey) }
    }
}
