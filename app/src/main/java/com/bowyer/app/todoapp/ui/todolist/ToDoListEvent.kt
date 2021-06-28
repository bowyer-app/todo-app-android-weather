package com.bowyer.app.todoapp.ui.todolist

import com.bowyer.app.todoapp.domain.ToDoContent
import com.bowyer.app.todoapp.ui.todolist.weather.ToDoListWeeklyWeatherItemModel

sealed class ToDoListEvent {
  class OpenEditToDo(val toDoContent: ToDoContent) : ToDoListEvent()
  object OpenInsertToDo : ToDoListEvent()
  class PresentWeeklyWeatherBottomSheet(val weeklyWeather: ToDoListWeeklyWeatherItemModel) :
    ToDoListEvent()
}
