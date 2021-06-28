package com.bowyer.app.todoapp.data.datasource

import com.bowyer.app.todoapp.data.remote.OpenWeatherMapApi
import com.bowyer.app.todoapp.data.repository.WeatherRepository
import com.bowyer.app.todoapp.data.translator.toWeatherList
import com.bowyer.app.todoapp.domain.Geolocation
import com.bowyer.app.todoapp.domain.OpenWeatherAppId
import com.bowyer.app.todoapp.domain.Weather
import javax.inject.Inject

class WeatherDataSource @Inject constructor(
  private val weatherMapApi: OpenWeatherMapApi
) : WeatherRepository {
  companion object {
    private const val LANG = "ja"
    private const val UNIT = "metric"
    private const val EXCLUDE = "minutely,hourly,alerts"
  }

  override suspend fun getWeeklyWeather(
    appId: OpenWeatherAppId,
    location: Geolocation
  ): List<Weather> {
    return weatherMapApi.getDaily(
      lat = location.latitude.value.toFloat(),
      lon = location.longitude.value.toFloat(),
      appId = appId.value,
      lang = LANG,
      units = UNIT,
      exclude = EXCLUDE
    ).toWeatherList()
  }
}
