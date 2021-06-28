package com.bowyer.app.todoapp.ui.setting

import androidx.annotation.StringRes
import com.bowyer.app.todoapp.ui.setting.SettingItemModel.OpenWeatherSetting
import com.bowyer.app.todoapp.ui.setting.SettingItemModel.ZipCodeSetting

sealed class SettingEvent {
  data class PresentAppIdDialog(val itemModel: OpenWeatherSetting) : SettingEvent()
  data class PresentZipCodeDialog(val itemModel: ZipCodeSetting) : SettingEvent()
  object PresentLicensePage : SettingEvent()
  data class PresentErrorDialog(
    @StringRes val title: Int,
    @StringRes val message: Int
  ) : SettingEvent()
}
