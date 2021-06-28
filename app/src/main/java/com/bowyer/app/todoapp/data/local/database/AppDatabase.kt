package com.bowyer.app.todoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bowyer.app.todoapp.data.local.database.dao.ToDoDao
import com.bowyer.app.todoapp.data.local.database.entity.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun todoDao(): ToDoDao
}
