package com.bowyer.app.todoapp.data.remote.model

import com.squareup.moshi.Json

data class WeeklyWeatherResponse(
  @field:Json(name = "daily") val daily: List<DailyWeather>,
  @field:Json(name = "current") val current: CurrentWeather
)
