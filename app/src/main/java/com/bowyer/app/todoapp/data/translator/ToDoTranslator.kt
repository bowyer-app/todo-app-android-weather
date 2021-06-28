package com.bowyer.app.todoapp.data.translator

import com.bowyer.app.todoapp.data.local.database.entity.ToDoEntity
import com.bowyer.app.todoapp.domain.DateTimeVO
import com.bowyer.app.todoapp.domain.ToDoContent
import com.bowyer.app.todoapp.domain.ToDoId

fun ToDoEntity.toToDoContent() =
  ToDoContent(
    id = ToDoId(id),
    title = title,
    description = description,
    dateTime = DateTimeVO(dateTime),
    isDone = isDone
  )
