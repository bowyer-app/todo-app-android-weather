package com.bowyer.app.todoapp.ui.todolist

import com.bowyer.app.todoapp.domain.DateTimeVO
import com.bowyer.app.todoapp.domain.ToDoContent
import com.bowyer.app.todoapp.domain.ToDoId
import com.bowyer.app.todoapp.ext.yyyyMMdd

data class ToDoListItemModel(
  val id: ToDoId,
  val title: String,
  val description: String,
  val dateTime: DateTimeVO,
  val isDone: Boolean
) {
  companion object {
    fun fromContent(content: ToDoContent) = ToDoListItemModel(
      id = content.id,
      title = content.title,
      description = content.description,
      dateTime = content.dateTime,
      isDone = content.isDone
    )
  }

  val created: String = dateTime.yyyyMMdd()
}
