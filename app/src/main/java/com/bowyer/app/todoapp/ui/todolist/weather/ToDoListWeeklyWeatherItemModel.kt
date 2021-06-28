package com.bowyer.app.todoapp.ui.todolist.weather

import android.os.Parcelable
import com.bowyer.app.todoapp.domain.Weather
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToDoListWeeklyWeatherItemModel(
  val weeklyWeather: List<ToDoListWeatherItemModel>
) : Parcelable {
  companion object {
    fun from(weatherList: List<Weather>, localeName: String) =
      ToDoListWeeklyWeatherItemModel(
        weeklyWeather = weatherList.map { weather ->
          ToDoListWeatherItemModel.fromWeather(
            weather = weather,
            localeName = localeName
          )
        }
      )
  }
}
