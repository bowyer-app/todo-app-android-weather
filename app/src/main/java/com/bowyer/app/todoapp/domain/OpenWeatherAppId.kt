package com.bowyer.app.todoapp.domain

/**
 * OpenWeatherのAPIを叩くためのAppId
 */
data class OpenWeatherAppId(
  val value: String
) {
  val isEmpty = value.isEmpty()
}
