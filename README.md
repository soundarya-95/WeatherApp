# Weather App 🌤️

A clean architecture-based weather app that fetches and displays weather information for a city or the user's current location. Built with **Jetpack Compose**, **Hilt**, **Retrofit**, and **Coroutines**.

---

## Features
- Search weather by city name.
- Fetch weather using the user's current location.
- Display weather details including temperature, condition, and icons.
- Auto-loads the last searched city on app launch.
- Prompts for location permission and displays a message if denied.
- Image caching for weather icons.
- Modular clean architecture.

---

## Screenshots

https://github.com/user-attachments/assets/6eceec08-6caa-4e80-8ebb-ae7c11ef0563

---

## Tech Stack
- **Kotlin**: Programming language.
- **Jetpack Compose**: UI toolkit.
- **Hilt**: Dependency injection.
- **Retrofit**: REST API client.
- **Moshi**: JSON parser.
- **Coroutines**: Asynchronous programming.
- **FusedLocationProvider**: Location services.
- **SharedPreferences**: Data persistence.

---

## Architecture
The project follows **Clean Architecture**, dividing the codebase into three layers:
1. **Presentation**: UI-related code, ViewModels, and state management.
2. **Domain**: Business logic, use cases, and abstractions.
3. **Data**: Repositories, APIs, and data sources.

---

## Setup Instructions

### Prerequisites
- Android Studio Arctic Fox or newer.
- Minimum SDK version: 21.
- OpenWeatherMap API Key (create a free account at [OpenWeatherMap](https://openweathermap.org)).

### Clone the Repository
```bash
git clone https://github.com/soundarya-95/WeatherApp.git
