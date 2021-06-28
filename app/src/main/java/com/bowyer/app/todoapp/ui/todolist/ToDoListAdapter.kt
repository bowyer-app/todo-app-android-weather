package com.bowyer.app.todoapp.ui.todolist

import com.bowyer.app.todoapp.ui.todolist.weather.ToDoListWeatherItem
import com.bowyer.app.todoapp.ui.todolist.weather.ToDoListWeatherItemModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import javax.inject.Inject

class ToDoListAdapter @Inject constructor(
  private val todoItemFactory: ToDoListItem.Factory,
  private val weatherItemFactory: ToDoListWeatherItem.Factory
) : GroupAdapter<GroupieViewHolder>() {
  private val weatherSection = Section()
  private val todoSection = Section()

  init {
    add(weatherSection)
    add(todoSection)
  }

  fun updateWeather(
    model: ToDoListWeatherItemModel,
    onClickItem: () -> Unit
  ) {
    weatherSection.update(
      listOf(
        weatherItemFactory.create(
          model = model,
          onClickItem = onClickItem
        )
      )
    )
  }

  fun updateTodo(
    list: List<ToDoListItemModel>,
    onClickItemListener: ToDoListItem.OnClickItemListener
  ) {
    val items = mutableListOf<Item<*>>()
    list.map { model ->
      items.add(
        todoItemFactory.create(
          model = model,
          onClickItemListener = onClickItemListener
        )
      )
    }
    todoSection.update(items)
  }
}
