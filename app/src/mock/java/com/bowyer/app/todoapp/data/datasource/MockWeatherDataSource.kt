package com.bowyer.app.todoapp.data.datasource

import com.bowyer.app.todoapp.data.repository.WeatherRepository
import com.bowyer.app.todoapp.domain.*
import org.joda.time.DateTime
import javax.inject.Inject

class MockWeatherDataSource @Inject constructor() : WeatherRepository {
  override suspend fun getWeeklyWeather(
    appId: OpenWeatherAppId,
    location: Geolocation
  ): List<Weather> {
    return createMockWeatherList()
  }

  private fun createMockWeatherList(): List<Weather> {
    return (0..7).map { index ->
      createMockWeather(index)
    }
  }

  private fun createMockWeather(index: Int): Weather {
    val dateTime = DateTime.now().plusDays(index)
    // OpenWeatherAPIの日付はUNIX TIMEなので1000で割っておく
    val dateTimeVO = DateTimeVO(dateTime.millis / 1000)
    val weatherType = WeatherType.values().random()
    return Weather(
      dateTime = dateTimeVO,
      description = weatherType.toDescription(),
      weatherType = weatherType,
      nowTemperature = Temperature(26.0f),
      maxTemperature = Temperature(30.0f),
      minTemperature = Temperature(16.0f)
    )
  }

  private fun WeatherType.toDescription(): WeatherDescription {
    return when (this) {
      WeatherType.CLEAR_SKY -> {
        WeatherDescription("快晴")
      }
      WeatherType.FEW_CLOUDS -> {
        WeatherDescription("晴れ")
      }
      WeatherType.SCATTERED_CLOUDS -> {
        WeatherDescription("くもり")
      }
      WeatherType.BROKEN_CLOUDS -> {
        WeatherDescription("くもり")
      }
      WeatherType.SHOWER_RAIN -> {
        WeatherDescription("小雨")
      }
      WeatherType.RAIN -> {
        WeatherDescription("雨")
      }
      WeatherType.THUNDERSTORM -> {
        WeatherDescription("雷雨")
      }
      WeatherType.SNOW -> {
        WeatherDescription("雪")
      }
      WeatherType.MIST -> {
        WeatherDescription("霧")
      }
    }
  }
}
