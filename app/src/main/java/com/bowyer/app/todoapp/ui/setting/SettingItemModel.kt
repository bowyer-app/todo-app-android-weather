package com.bowyer.app.todoapp.ui.setting

import androidx.annotation.StringRes
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.domain.OpenWeatherAppId
import com.bowyer.app.todoapp.domain.ZipCode

sealed class SettingItemModel(
  @StringRes val title: Int,
  @StringRes val hintText: Int,
  val description: String?
) {
  data class OpenWeatherSetting(
    val openWeatherAppId: OpenWeatherAppId?
  ) : SettingItemModel(
    title = R.string.open_weather_app_id,
    hintText = R.string.open_weather_app_id_hint,
    description = openWeatherAppId?.value
  )

  data class ZipCodeSetting(
    val zipCode: ZipCode?
  ) : SettingItemModel(
    title = R.string.zip_code,
    hintText = R.string.zip_code_hint,
    description = zipCode?.value
  )

  class LicenseSetting : SettingItemModel(
    title = R.string.title_license,
    hintText = R.string.title_license,
    description = null
  )
}
