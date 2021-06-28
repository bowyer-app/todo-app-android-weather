package com.bowyer.app.todoapp.data.datasource

import com.bowyer.app.todoapp.data.local.database.dao.ToDoDao
import com.bowyer.app.todoapp.data.local.database.entity.ToDoEntity
import com.bowyer.app.todoapp.data.repository.ToDoRepository
import com.bowyer.app.todoapp.data.translator.toToDoContent
import com.bowyer.app.todoapp.domain.ToDoContent
import com.bowyer.app.todoapp.domain.ToDoId
import javax.inject.Inject

class ToDoDataSource @Inject constructor(
  private val dao: ToDoDao
) : ToDoRepository {
  override suspend fun getAll(): List<ToDoContent> =
    dao.getAllToDo().map {
      it.toToDoContent()
    }

  override suspend fun getToDoById(id: ToDoId): List<ToDoContent> =
    dao.getToDoById(id.value).map {
      it.toToDoContent()
    }

  override suspend fun delete(todo: ToDoContent) =
    dao.delete(toEntity(todo))

  override suspend fun save(todo: ToDoContent) {
    if (todo.id == ToDoId.DEFAULT) {
      insert(todo)
    } else {
      dao.update(
        toDoEntity = toEntity(todo = todo)
      )
    }
  }

  private suspend fun insert(todo: ToDoContent) {
    val size = dao.getAllToDo().size + 1
    val content = todo.copy(
      id = ToDoId(size.toLong())
    )
    dao.insert(
      toDoEntity = toEntity(
        todo = content
      )
    )
  }

  private fun toEntity(todo: ToDoContent) =
    ToDoEntity().apply {
      id = todo.id.value
      title = todo.title
      description = todo.description
      dateTime = todo.dateTime.value
      isDone = todo.isDone
    }
}
