package com.bowyer.app.todoapp.data.repository

import com.bowyer.app.todoapp.domain.OpenWeatherAppId
import com.bowyer.app.todoapp.domain.ZipCode

interface SettingRepository {
  fun getOpenWeatherAppId(): OpenWeatherAppId?
  fun saveOpenWeatherAppId(appId: OpenWeatherAppId)
  fun getZipCode(): ZipCode?
  fun saveZipCode(zipCode: ZipCode)
}
