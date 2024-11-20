package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.domain.model.WeatherInfo
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WeatherResponseMapperTest {

    private lateinit var weatherResponseMapper: WeatherResponseMapper

    @Before
    fun setUp() {
        weatherResponseMapper = WeatherResponseMapper()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `mapWeatherResponse should correctly map WeatherResponse to WeatherInfo`() {
        // Arrange
        val weatherResponse = mockk<WeatherResponse>()
        val weather = listOf(mockk<Weather>())
        val main = mockk<Main>()

        val description = "Clear sky"
        val icon = "01d"
        val temperature = 25.0
        val humidity = 60
        val feelsLike = 23.0

        // Mock the properties
        every { weatherResponse.name } returns "New York"
        every { weatherResponse.weather } returns weather
        every { weatherResponse.main } returns main
        every { weather[0].description } returns description
        every { weather[0].icon } returns icon
        every { main.temp } returns temperature
        every { main.humidity } returns humidity
        every { main.feelsLike } returns feelsLike

        val expectedWeatherInfo = WeatherInfo(
            city = "New York",
            description = description,
            feelsLike = feelsLike,
            icon = icon,
            humidity = humidity,
            temperature = temperature
        )

        // Act
        val result = weatherResponseMapper.mapWeatherResponse(weatherResponse)

        // Assert
        assertEquals(expectedWeatherInfo, result)

        // Verify interactions with mock objects
        verify { weatherResponse.name }
        verify { weatherResponse.weather }
        verify { weatherResponse.main }
        verify { weather[0].description }
        verify { weather[0].icon }
        verify { main.temp }
        verify { main.humidity }
        verify { main.feelsLike }
    }

    @Test
    fun `mapWeatherResponse should return default description when weather description is null`() {
        // Arrange
        val weatherResponse = mockk<WeatherResponse>()
        val weather = listOf(mockk<Weather>())
        val main = mockk<Main>()

        val description:String? = null
        val icon = "01d"
        val temperature = 25.0
        val humidity = 60
        val feelsLike = 23.0

        // Mock the properties
        every { weatherResponse.name } returns "New York"
        every { weatherResponse.weather } returns weather
        every { weatherResponse.main } returns main
        every { weather[0].description } returns description
        every { weather[0].icon } returns icon
        every { main.temp } returns temperature
        every { main.humidity } returns humidity
        every { main.feelsLike } returns feelsLike

        val expectedWeatherInfo = WeatherInfo(
            city = "New York",
            description = "No description", // Default description when null
            feelsLike = feelsLike,
            icon = icon,
            humidity = humidity,
            temperature = temperature
        )

        // Act
        val result = weatherResponseMapper.mapWeatherResponse(weatherResponse)

        // Assert
        assertEquals(expectedWeatherInfo, result)
    }

    @Test
    fun `mapWeatherResponse should return empty icon when weather icon is null or empty`() {
        // Arrange
        val weatherResponse = mockk<WeatherResponse>()
        val weather = listOf(mockk<Weather>())
        val main = mockk<Main>()

        val description = "Clear sky"
        val icon = ""
        val temperature = 25.0
        val humidity = 60
        val feelsLike = 23.0

        // Mock the properties
        every { weatherResponse.name } returns "New York"
        every { weatherResponse.weather } returns weather
        every { weatherResponse.main } returns main
        every { weather[0].description } returns description
        every { weather[0].icon } returns icon
        every { main.temp } returns temperature
        every { main.humidity } returns humidity
        every { main.feelsLike } returns feelsLike

        val expectedWeatherInfo = WeatherInfo(
            city = "New York",
            description = description,
            feelsLike = feelsLike,
            icon = "", // Empty icon string
            humidity = humidity,
            temperature = temperature
        )

        // Act
        val result = weatherResponseMapper.mapWeatherResponse(weatherResponse)

        // Assert
        assertEquals(expectedWeatherInfo, result)
    }
}
