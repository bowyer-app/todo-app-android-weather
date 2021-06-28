package com.bowyer.app.todoapp.data.remote.model

import com.squareup.moshi.Json

data class DailyTemperature(
  @field:Json(name = "min") val min: Float,
  @field:Json(name = "max") val max: Float
)
