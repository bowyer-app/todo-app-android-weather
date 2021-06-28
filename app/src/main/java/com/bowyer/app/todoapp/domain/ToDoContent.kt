package com.bowyer.app.todoapp.domain

import java.io.Serializable

/**
 * ToDo内容
 */
data class ToDoContent(
  val id: ToDoId,
  val title: String,
  val description: String,
  val dateTime: DateTimeVO,
  val isDone: Boolean
) : Serializable
