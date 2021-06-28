package com.bowyer.app.todoapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bowyer.app.todoapp.Const.Companion.BLANK

@Entity
class ToDoEntity {
  companion object {
    const val TABLE = "todoEntity"
    const val ID = "id"
    const val TITLE = "title"
    const val DESCRIPTION = "description"
    const val DATE_TIME = "dateTime"
    const val IS_DONE = "isDone"
  }

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = ID)
  var id: Long = 0

  @ColumnInfo(name = TITLE)
  var title: String = BLANK

  @ColumnInfo(name = DESCRIPTION)
  var description: String = BLANK

  @ColumnInfo(name = DATE_TIME)
  var dateTime: Long = 0

  @ColumnInfo(name = IS_DONE)
  var isDone: Boolean = false
}
