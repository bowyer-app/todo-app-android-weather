package com.bowyer.app.todoapp.domain

/**
 * 郵便番号
 */
data class ZipCode(
  val value: String
) {
  companion object {
    private val regex = Regex("^[0-9]{3}-[0-9]{4}\$")
  }

  val isValidZipCode = regex.matches(value)
}
