package com.bowyer.app.todoapp.ui.license

sealed class LicenseEvent {
  data class PresentLicensePage(val url: String) : LicenseEvent()
}
