package com.bowyer.app.todoapp.data.translator

import com.bowyer.app.todoapp.data.remote.model.DailyWeather
import com.bowyer.app.todoapp.data.remote.model.WeeklyWeatherResponse
import com.bowyer.app.todoapp.domain.DateTimeVO
import com.bowyer.app.todoapp.domain.Temperature
import com.bowyer.app.todoapp.domain.Weather
import com.bowyer.app.todoapp.domain.WeatherDescription
import com.bowyer.app.todoapp.ext.toWeatherType

fun WeeklyWeatherResponse.toWeatherList(): List<Weather> {
  return daily.map { it.toWeather(current.temp) }
}

fun DailyWeather.toWeather(currentTemperature: Float) = Weather(
  description = WeatherDescription(weather.first().description),
  weatherType = weather.first().icon.toWeatherType(),
  nowTemperature = Temperature(currentTemperature),
  maxTemperature = Temperature(temp.max),
  minTemperature = Temperature(temp.min),
  dateTime = DateTimeVO(dt)
)
