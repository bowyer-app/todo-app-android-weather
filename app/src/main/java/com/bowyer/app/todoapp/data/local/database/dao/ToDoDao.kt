package com.bowyer.app.todoapp.data.local.database.dao

import androidx.room.*
import com.bowyer.app.todoapp.data.local.database.entity.ToDoEntity

@Dao
interface ToDoDao {

  @Query("SELECT * FROM ${ToDoEntity.TABLE} order by ${ToDoEntity.ID} DESC")
  suspend fun getAllToDo(): List<ToDoEntity>

  @Query("SELECT * FROM ${ToDoEntity.TABLE} WHERE ${ToDoEntity.ID} =(:id)")
  suspend fun getToDoById(id: Long): List<ToDoEntity>

  @Insert
  suspend fun insert(toDoEntity: ToDoEntity)

  @Update
  suspend fun update(toDoEntity: ToDoEntity)

  @Delete
  suspend fun delete(toDoEntity: ToDoEntity)
}
