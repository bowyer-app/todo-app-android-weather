package com.bowyer.app.todoapp.data.datasource

import com.bowyer.app.todoapp.data.repository.SettingRepository
import com.bowyer.app.todoapp.domain.OpenWeatherAppId
import com.bowyer.app.todoapp.domain.ZipCode
import javax.inject.Inject

class MockSettingDataSource @Inject constructor() : SettingRepository {
  override fun getOpenWeatherAppId(): OpenWeatherAppId? {
    return OpenWeatherAppId("mockappId")
  }

  override fun saveOpenWeatherAppId(appId: OpenWeatherAppId) {
    // do nothing
  }

  override fun getZipCode(): ZipCode? {
    return ZipCode("150-0001")
  }

  override fun saveZipCode(zipCode: ZipCode) {
    // do nothing
  }
}
