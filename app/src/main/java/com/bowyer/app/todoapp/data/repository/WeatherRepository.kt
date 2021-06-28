package com.bowyer.app.todoapp.data.repository

import com.bowyer.app.todoapp.domain.Geolocation
import com.bowyer.app.todoapp.domain.OpenWeatherAppId
import com.bowyer.app.todoapp.domain.Weather

interface WeatherRepository {
  suspend fun getWeeklyWeather(appId: OpenWeatherAppId, location: Geolocation): List<Weather>
}
