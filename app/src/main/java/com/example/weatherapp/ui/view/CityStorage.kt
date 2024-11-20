package com.example.weatherapp.ui.view

import android.content.SharedPreferences
import javax.inject.Inject

class CityStorage @Inject constructor(private val sharedPreferences: SharedPreferences) {

    /**
     * Stores the city in the sharedprefs
     * @param city - name of the city to store
     * */
    fun storeCity(city: String) {
        sharedPreferences.edit().putString(CITY_PREF, city)
            .apply()
    }

    /**
     * retrieves the city stored if available, otherwise empty
     * */
    fun getCity(): String {
        return sharedPreferences.getString(CITY_PREF, "").orEmpty()
    }

    companion object {
        private const val CITY_PREF = "city_pref"
    }
}