package com.bowyer.app.todoapp.data.repository

import com.bowyer.app.todoapp.domain.ToDoContent
import com.bowyer.app.todoapp.domain.ToDoId

interface ToDoRepository {
  suspend fun getAll(): List<ToDoContent>
  suspend fun getToDoById(id: ToDoId): List<ToDoContent>
  suspend fun delete(todo: ToDoContent)
  suspend fun save(todo: ToDoContent)
}
