package com.example.weatherapp.ui.view

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(viewModel: WeatherViewModel = viewModel(), cityStorage: CityStorage) {

    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var city by remember { mutableStateOf("") }
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    // Activity Result contract for Location access
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted

        // if permission is granted get the current location and fetch weather
        if (isGranted) {
            getCurrentLocationAndFetchWeather(context, viewModel)
        } else {
            // show Location permission is not granted
            Toast.makeText(
                context,
                context.getString(R.string.location_permission_not_granted), Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.weather_app)) }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text(stringResource(R.string.enter_city_name)) },
                modifier = Modifier.fillMaxWidth(),

                // keyboard actions/options to listen Done click
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        cityStorage.storeCity(city)
                        keyboardController?.hide()
                        viewModel.fetchWeatherByCity(city = city)
                    }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Button to use current location and launch location permission on click
            Button(onClick = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }) {
                Text(stringResource(R.string.use_current_location))
            }
            // Circular Progress Indicator that shows when loading
            if (uiState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weather details of city or location requested
            Text("City: ${uiState.city}")
            Text("Temperature: ${uiState.temperature}")
            Text("Feels Like: ${uiState.feelsLike}")
            Text("Humidity: ${uiState.humidity}")
            Text("Description: ${uiState.description}")
            AsyncImage(model = uiState.iconUrl, contentDescription = null)

            // error state: Shows the error message
            uiState.errorMessage?.let {
                Text(
                    "Error: $it",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

/**
 * Fetches the current location of the user if the location permission is granted
 * @param context - current context
 * @param viewModel- instance of weatherViewmodel to fetch the weather by coordinates
 * */
fun getCurrentLocationAndFetchWeather(context: Context, viewModel: WeatherViewModel) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            viewModel.fetchWeatherByCoordinates(latitude, longitude)
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.failed_to_get_current_location), Toast.LENGTH_SHORT
            ).show()
        }
    }.addOnFailureListener {
        Toast.makeText(
            context,
            context.getString(R.string.unable_to_retrieve_location), Toast.LENGTH_SHORT
        ).show()
    }
}
