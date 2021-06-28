package com.bowyer.app.todoapp.ui.todolist

import com.bowyer.app.todoapp.domain.Geolocation
import com.bowyer.app.todoapp.domain.OpenWeatherAppId
import com.bowyer.app.todoapp.domain.RefreshState
import com.bowyer.app.todoapp.ui.todolist.weather.WeatherState

data class ToDoListState(
  val todoList: List<ToDoListItemModel> = listOf(),
  val weatherState: WeatherState? = WeatherState(),
  val openWeatherAppId: OpenWeatherAppId? = null,
  val geoLocation: Geolocation? = null,
  val refreshState: RefreshState = RefreshState.LOADING
)
