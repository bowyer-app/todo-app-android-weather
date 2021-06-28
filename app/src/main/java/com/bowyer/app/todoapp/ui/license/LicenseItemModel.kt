package com.bowyer.app.todoapp.ui.license

import com.bowyer.app.todoapp.domain.LibraryContent

data class LicenseItemModel(
  val libraryName: String,
  val copyrightHolder: String?,
  val license: String,
  val url: String?
) {
  companion object {
    fun from(content: LibraryContent) =
      LicenseItemModel(
        libraryName = content.libraryName,
        copyrightHolder = content.copyrightHolder,
        license = content.license,
        url = content.url
      )
  }
}
