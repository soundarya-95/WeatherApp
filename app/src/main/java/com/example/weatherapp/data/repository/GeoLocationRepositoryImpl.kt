package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.GeoCodeApi
import com.example.weatherapp.domain.model.GeoLocationInfo
import com.example.weatherapp.domain.repository.GeoLocationRepository
import javax.inject.Inject

class GeoLocationRepositoryImpl @Inject constructor(
    private val geoCodeApi: GeoCodeApi,
    private val getLocationMapper: GeoLocationMapper
) : GeoLocationRepository {
    override suspend fun getGeoLocationForCity(city: String, apiKey: String): GeoLocationInfo {
        val response = geoCodeApi.getGeoCode(city, apiKey)
        return getLocationMapper.mapGeoLocation(response)
    }
}