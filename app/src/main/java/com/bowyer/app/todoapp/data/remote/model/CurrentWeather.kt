package com.bowyer.app.todoapp.data.remote.model

import com.squareup.moshi.Json

data class CurrentWeather(
  @field:Json(name = "temp") val temp: Float
)
