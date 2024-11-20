package com.example.weatherapp.data.hilt

import com.example.weatherapp.data.api.GeoCodeApi
import com.example.weatherapp.data.api.RetrofitInstance
import com.example.weatherapp.data.api.WeatherApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideWeatherAPI(retrofitInstance: RetrofitInstance): WeatherApi {
        return retrofitInstance.createApi(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGeoCodeAPI(retrofitInstance: RetrofitInstance): GeoCodeApi {
        return retrofitInstance.createApi(GeoCodeApi::class.java)
    }

    @Provides
    @Singleton
    fun bindConverterFactory(): Converter.Factory {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return MoshiConverterFactory.create(moshi)
    }
}