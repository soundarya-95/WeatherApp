package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.GeoLocationInfo
import com.example.weatherapp.domain.repository.GeoLocationRepository
import javax.inject.Inject
import javax.inject.Named

interface GeoLocationUseCase {

    suspend fun getGeoLocationForCity(city: String): GeoLocationInfo

}

class GeoLocationUseCaseImpl @Inject constructor(
    private val geoLocationRepository: GeoLocationRepository,
    @Named("weatherApiKey") private val apiKey: String
) : GeoLocationUseCase {

    override suspend fun getGeoLocationForCity(city: String): GeoLocationInfo {
        return geoLocationRepository.getGeoLocationForCity(city = city, apiKey = apiKey)
    }

}

