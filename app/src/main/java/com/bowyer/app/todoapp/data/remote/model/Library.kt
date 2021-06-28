package com.bowyer.app.todoapp.data.remote.model

import com.squareup.moshi.Json

data class Library(
  @field:Json(name = "libraryName") val libraryName: String,
  @field:Json(name = "copyrightHolder") val copyrightHolder: String?,
  @field:Json(name = "license") val license: String,
  @field:Json(name = "url") val url: String?
)
