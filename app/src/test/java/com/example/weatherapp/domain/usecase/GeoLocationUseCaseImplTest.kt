package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.GeoLocationInfo
import com.example.weatherapp.domain.repository.GeoLocationRepository
import com.example.weatherapp.domain.usecase.GeoLocationUseCaseImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GeoLocationUseCaseImplTest {

    private val geoLocationRepository: GeoLocationRepository = mockk()
    private lateinit var geoLocationUseCaseImpl: GeoLocationUseCaseImpl
    private val apiKey = "testApiKey"

    @Before
    fun setUp() {
        geoLocationUseCaseImpl = GeoLocationUseCaseImpl(geoLocationRepository, apiKey)
    }

    @After
    fun tearDown() {
        clearMocks(geoLocationRepository)
    }

    @Test
    fun `getGeoLocationForCity should return geo location info when repository call is successful`() = runTest {
        // Arrange
        val city = "New York"
        val geoLocationInfo = GeoLocationInfo(lat = 40.7128, lon = -74.0060)

        // Mock the repository to return the geoLocationInfo
        coEvery { geoLocationRepository.getGeoLocationForCity(city, apiKey) } returns geoLocationInfo

        // Act
        val result = geoLocationUseCaseImpl.getGeoLocationForCity(city)

        // Assert
        assertEquals(geoLocationInfo, result)
        coVerify { geoLocationRepository.getGeoLocationForCity(city, apiKey) }
    }

    @Test
    fun `getGeoLocationForCity should throw an exception when repository call fails`() = runTest {
        // Arrange
        val city = "InvalidCity"
        val exceptionMessage = "City not found"
        coEvery { geoLocationRepository.getGeoLocationForCity(city, apiKey) } throws RuntimeException(exceptionMessage)

        // Act & Assert
        try {
            geoLocationUseCaseImpl.getGeoLocationForCity(city)
        } catch (e: Exception) {
            assertEquals(exceptionMessage, e.message)
        }

        coVerify { geoLocationRepository.getGeoLocationForCity(city, apiKey) }
    }
}
