package com.bowyer.app.todoapp.domain

import java.io.Serializable

/**
 * ToDoのId
 */
data class ToDoId(
  val value: Long
) : Serializable {
  companion object {
    private const val DEFAULT_VALUE = -1L
    val DEFAULT = ToDoId(DEFAULT_VALUE)
  }
}

