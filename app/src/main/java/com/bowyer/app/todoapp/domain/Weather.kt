package com.bowyer.app.todoapp.domain

/**
 * 天気予報
 */
data class Weather(
  val description: WeatherDescription,
  val weatherType: WeatherType,
  val nowTemperature: Temperature,
  val maxTemperature: Temperature,
  val minTemperature: Temperature,
  val dateTime: DateTimeVO
)
