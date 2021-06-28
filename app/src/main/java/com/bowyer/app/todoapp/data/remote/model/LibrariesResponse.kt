package com.bowyer.app.todoapp.data.remote.model

import com.squareup.moshi.Json

data class LibrariesResponse(
  @field:Json(name = "libraries") val libraries: List<Library>
)
