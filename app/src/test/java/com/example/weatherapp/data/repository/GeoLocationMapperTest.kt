package com.example.weatherapp.data.repository


import com.example.weatherapp.data.model.GeoLocation
import com.example.weatherapp.domain.model.GeoLocationInfo
import io.mockk.mockk
import io.mockk.every
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GeoLocationMapperTest {

    private lateinit var geoLocationMapper: GeoLocationMapper

    @Before
    fun setUp() {
        geoLocationMapper = GeoLocationMapper()
    }

    @Test
    fun `mapGeoLocation should map first GeoLocation to GeoLocationInfo`() {
        // Arrange
        val geoLocation = mockk<GeoLocation>()
        val geoLocationList = listOf(geoLocation)

        val expectedLat = 40.7128
        val expectedLon = -74.0060

        // Mock the GeoLocation properties
        every { geoLocation.lat } returns expectedLat
        every { geoLocation.lon } returns expectedLon

        val expectedGeoLocationInfo = GeoLocationInfo(lat = expectedLat, lon = expectedLon)

        // Act
        val result = geoLocationMapper.mapGeoLocation(geoLocationList)

        // Assert
        assertEquals(expectedGeoLocationInfo, result)
    }

    @Test(expected = NoSuchElementException::class)
    fun `mapGeoLocation should throw NoSuchElementException when list is empty`() {
        // Arrange
        val emptyGeoLocationList = emptyList<GeoLocation>()

        // Act
        geoLocationMapper.mapGeoLocation(emptyGeoLocationList)

        // Assert
        // Exception is expected
    }
}
