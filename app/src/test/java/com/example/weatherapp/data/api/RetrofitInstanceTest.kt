package com.example.weatherapp.data.api

import okhttp3.OkHttpClient
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Converter
import retrofit2.Retrofit
import io.mockk.mockk
import io.mockk.verify

class RetrofitInstanceTest {

    private lateinit var retrofitInstance: RetrofitInstance
    private val mockConverterFactory: Converter.Factory = mockk()
    private val mockOkHttpClient: OkHttpClient = mockk()

    @Before
    fun setUp() {
        retrofitInstance = RetrofitInstance(
            converterFactory = mockConverterFactory,
            okHttpClient = mockOkHttpClient
        )
    }

    @Test
    fun `createApi should create a Retrofit API instance`() {
        // Arrange
        val mockApiClass = TestApi::class.java

        // Act
        val apiInstance = retrofitInstance.createApi(mockApiClass)

        // Assert
        assertNotNull("API instance should not be null", apiInstance)
        assert(apiInstance is TestApi)
    }

    // Define a mock API interface for testing
    interface TestApi {
        // Example endpoint
    }
}
