package com.bowyer.app.todoapp.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.data.repository.SettingRepository
import com.bowyer.app.todoapp.domain.OpenWeatherAppId
import com.bowyer.app.todoapp.domain.ZipCode
import com.bowyer.app.todoapp.ui.setting.SettingEvent.*
import com.bowyer.app.todoapp.ui.setting.SettingItemModel.OpenWeatherSetting
import com.bowyer.app.todoapp.ui.setting.SettingItemModel.ZipCodeSetting
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingViewModel @Inject constructor(
  private val settingRepository: SettingRepository
) : ViewModel() {

  private val _state = MutableLiveData(
    SettingState()
  )
  val state: LiveData<SettingState> = _state

  private val _event = MutableSharedFlow<SettingEvent>()
  val event: SharedFlow<SettingEvent> get() = _event

  fun onCreateView() {
    loadSettings()
  }

  private fun loadSettings() {
    val settingList = mutableListOf<SettingItemModel>()
    val zipCodeSetting = ZipCodeSetting(
      zipCode = settingRepository.getZipCode()
    )
    settingList.add(zipCodeSetting)
    val apiSetting = OpenWeatherSetting(
      openWeatherAppId = settingRepository.getOpenWeatherAppId()
    )
    settingList.add(apiSetting)
    settingList.add(
      SettingItemModel.LicenseSetting()
    )
    _state.value = _state.value?.copy(
      settingList = settingList
    )
  }

  fun onClickSettingItem(model: SettingItemModel) {
    when (model) {
      is OpenWeatherSetting -> {
        viewModelScope.launch {
          _event.emit(PresentAppIdDialog(model))
        }
      }
      is ZipCodeSetting -> {
        viewModelScope.launch {
          _event.emit(PresentZipCodeDialog(model))
        }
      }
      is SettingItemModel.LicenseSetting -> {
        viewModelScope.launch {
          _event.emit(PresentLicensePage)
        }
      }
    }
  }

  fun onClickAppIdSave(value: String) {
    if (value.isEmpty()) {
      viewModelScope.launch {
        _event.emit(
          PresentErrorDialog(
            title = R.string.setting_error_dialog_title_need_value,
            message = R.string.setting_error_dialog_message_need_api
          )
        )
      }
      return
    }
    settingRepository.saveOpenWeatherAppId(
      appId = OpenWeatherAppId(value)
    )
    loadSettings()
  }

  fun onClickZipCodeSave(value: String) {
    if (value.isEmpty()) {
      viewModelScope.launch {
        _event.emit(
          PresentErrorDialog(
            title = R.string.setting_error_dialog_title_need_value,
            message = R.string.setting_error_dialog_message_need_zip_code
          )
        )
      }
      return
    }
    val zipCode = ZipCode(value)
    if (!zipCode.isValidZipCode) {
      viewModelScope.launch {
        _event.emit(
          PresentErrorDialog(
            title = R.string.zip_code_error_title,
            message = R.string.zip_code_error_message
          )
        )
      }
      return
    }
    settingRepository.saveZipCode(
      zipCode = zipCode
    )
    loadSettings()
  }
}
