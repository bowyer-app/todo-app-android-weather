package com.bowyer.app.todoapp.data.translator

import com.bowyer.app.todoapp.data.remote.model.Library
import com.bowyer.app.todoapp.domain.LibraryContent

fun Library.toLibraryContent() =
  LibraryContent(
    libraryName = libraryName,
    copyrightHolder = copyrightHolder,
    license = license,
    url = url
  )
