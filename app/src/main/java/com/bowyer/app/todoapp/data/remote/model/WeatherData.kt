package com.bowyer.app.todoapp.data.remote.model

import com.squareup.moshi.Json

data class WeatherData(
  @field:Json(name = "description") val description: String,
  @field:Json(name = "icon") val icon: String
)
