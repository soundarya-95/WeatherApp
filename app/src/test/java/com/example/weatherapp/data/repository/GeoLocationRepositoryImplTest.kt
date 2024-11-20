package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.GeoCodeApi
import com.example.weatherapp.data.model.GeoLocation
import com.example.weatherapp.domain.model.GeoLocationInfo
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GeoLocationRepositoryImplTest {

    private val geoCodeApi: GeoCodeApi = mockk()
    private val geoLocationMapper: GeoLocationMapper = mockk()
    private lateinit var geoLocationRepositoryImpl: GeoLocationRepositoryImpl
    private val apiKey = "testApiKey"

    @Before
    fun setUp() {
        geoLocationRepositoryImpl = GeoLocationRepositoryImpl(geoCodeApi, geoLocationMapper)
    }

    @After
    fun tearDown() {
        clearMocks(geoCodeApi, geoLocationMapper)
    }

    @Test
    fun `getGeoLocationForCity should return GeoLocationInfo when API call is successful`() = runTest {
        // Arrange
        val city = "New York"
        val response = mockk<List<GeoLocation>>() // Replace with the actual response type from GeoCodeApi
        val geoLocationInfo = GeoLocationInfo(lat = 40.7128, lon = -74.0060)

        // Mock the GeoCodeApi response
        coEvery { geoCodeApi.getGeoCode(city, apiKey) } returns response
        // Mock the GeoLocationMapper to map the response to GeoLocationInfo
        every { geoLocationMapper.mapGeoLocation(response) } returns geoLocationInfo

        // Act
        val result = geoLocationRepositoryImpl.getGeoLocationForCity(city, apiKey)

        // Assert
        assertEquals(geoLocationInfo, result)
        coVerify { geoCodeApi.getGeoCode(city, apiKey) }
        verify { geoLocationMapper.mapGeoLocation(response) }
    }

    @Test
    fun `getGeoLocationForCity should throw exception when API call fails`() = runTest {
        // Arrange
        val city = "InvalidCity"
        val exceptionMessage = "API request failed"

        // Mock the GeoCodeApi to throw an exception
        coEvery { geoCodeApi.getGeoCode(city, apiKey) } throws RuntimeException(exceptionMessage)

        // Act & Assert
        try {
            geoLocationRepositoryImpl.getGeoLocationForCity(city, apiKey)
        } catch (e: Exception) {
            assertEquals(exceptionMessage, e.message)
        }

        coVerify { geoCodeApi.getGeoCode(city, apiKey) }
    }
}
