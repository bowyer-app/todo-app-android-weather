package com.bowyer.app.todoapp.data.remote.model

import com.squareup.moshi.Json

data class DailyWeather(
  @field:Json(name = "dt") val dt: Long,
  @field:Json(name = "temp") val temp: DailyTemperature,
  @field:Json(name = "weather") val weather: List<WeatherData>
)
