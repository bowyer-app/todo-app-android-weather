package com.bowyer.app.todoapp.ui.todolist.weather

import android.os.Parcelable
import com.bowyer.app.todoapp.domain.DateTimeVO
import com.bowyer.app.todoapp.domain.Weather
import com.bowyer.app.todoapp.domain.WeatherType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToDoListWeatherItemModel(
  val localeName: String,
  val nowTemperature: Float,
  val maxTemperature: Float,
  val minTemperature: Float,
  val description: String,
  val weatherType: WeatherType,
  val dateTime: DateTimeVO
) : Parcelable {
  companion object {
    fun fromWeather(weather: Weather, localeName: String) = ToDoListWeatherItemModel(
      localeName = localeName,
      nowTemperature = weather.nowTemperature.value,
      maxTemperature = weather.maxTemperature.value,
      minTemperature = weather.minTemperature.value,
      description = weather.description.value,
      weatherType = weather.weatherType,
      dateTime = weather.dateTime
    )
  }
}
