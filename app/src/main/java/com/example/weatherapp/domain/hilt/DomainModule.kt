package com.example.weatherapp.domain.hilt

import com.example.weatherapp.data.repository.GeoLocationRepositoryImpl
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.GeoLocationRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.usecase.GeoLocationUseCase
import com.example.weatherapp.domain.usecase.GeoLocationUseCaseImpl
import com.example.weatherapp.domain.usecase.WeatherUseCase
import com.example.weatherapp.domain.usecase.WeatherUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindWeatherUseCase(weatherUseCaseImpl: WeatherUseCaseImpl): WeatherUseCase

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindGeoLocationUseCase(geoLocationUseCaseImpl: GeoLocationUseCaseImpl): GeoLocationUseCase

    @Binds
    @Singleton
    abstract fun bindGeoLocationRepository(geoLocationRepositoryImpl: GeoLocationRepositoryImpl): GeoLocationRepository
}