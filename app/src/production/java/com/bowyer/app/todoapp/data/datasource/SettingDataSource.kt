package com.bowyer.app.todoapp.data.datasource

import com.bowyer.app.todoapp.Const.Companion.BLANK
import com.bowyer.app.todoapp.data.local.preference.ToDoPreference
import com.bowyer.app.todoapp.data.repository.SettingRepository
import com.bowyer.app.todoapp.domain.OpenWeatherAppId
import com.bowyer.app.todoapp.domain.ZipCode
import javax.inject.Inject

class SettingDataSource @Inject constructor(
  private val toDoPreference: ToDoPreference
) : SettingRepository {
  companion object {
    private const val KEY_APP_ID = "appId"
    private const val KEY_ZIP_CODE = "zipcode"
  }

  override fun getOpenWeatherAppId(): OpenWeatherAppId? {
    return toDoPreference.getStringVal(KEY_APP_ID, BLANK)?.let { appId ->
      OpenWeatherAppId(appId)
    }
  }

  override fun saveOpenWeatherAppId(appId: OpenWeatherAppId) {
    toDoPreference.writeString(KEY_APP_ID, appId.value)
  }

  override fun getZipCode(): ZipCode? {
    return toDoPreference.getStringVal(KEY_ZIP_CODE, BLANK)?.let { zipCode ->
      ZipCode(zipCode)
    }
  }

  override fun saveZipCode(zipCode: ZipCode) {
    toDoPreference.writeString(KEY_ZIP_CODE, zipCode.value)
  }
}
