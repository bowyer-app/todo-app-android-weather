package com.bowyer.app.todoapp.ui.license

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowyer.app.todoapp.data.repository.LicenseRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LicenseViewModel @Inject constructor(
  private val repository: LicenseRepository
) : ViewModel() {

  private val _state = MutableLiveData(
    LicenseState()
  )
  val state: LiveData<LicenseState> = _state

  private val _event = MutableSharedFlow<LicenseEvent>()
  val event: SharedFlow<LicenseEvent> get() = _event

  fun onCreateView() {
    viewModelScope.launch {
      try {
        val libraries = repository.loadLicenses().map {
          LicenseItemModel.from(it)
        }
        _state.value = _state.value?.copy(
          licenseList = libraries
        )
      } catch (e: Throwable) {
        Timber.e(e)
      }
    }
  }

  fun onClickLicenseItem(model: LicenseItemModel) {
    val url = model.url ?: return
    viewModelScope.launch {
      _event.emit(LicenseEvent.PresentLicensePage(url))
    }
  }
}
