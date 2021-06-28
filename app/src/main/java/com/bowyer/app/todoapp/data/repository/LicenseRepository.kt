package com.bowyer.app.todoapp.data.repository

import com.bowyer.app.todoapp.domain.LibraryContent

interface LicenseRepository {
  suspend fun loadLicenses(): List<LibraryContent>
}
