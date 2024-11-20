package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.GeoLocation
import com.example.weatherapp.domain.model.GeoLocationInfo
import javax.inject.Inject

class GeoLocationMapper @Inject constructor() {
    fun mapGeoLocation(geoLocation: List<GeoLocation>): GeoLocationInfo {
        return with(geoLocation.first()) {
            GeoLocationInfo(lat, lon)
        }
    }
}