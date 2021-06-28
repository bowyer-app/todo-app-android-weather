package com.bowyer.app.todoapp.ui.todolist

import android.content.Context
import android.location.Geocoder
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowyer.app.todoapp.data.repository.SettingRepository
import com.bowyer.app.todoapp.data.repository.ToDoRepository
import com.bowyer.app.todoapp.data.repository.WeatherRepository
import com.bowyer.app.todoapp.domain.*
import com.bowyer.app.todoapp.ui.todolist.weather.ToDoListWeatherItemModel
import com.bowyer.app.todoapp.ui.todolist.weather.ToDoListWeeklyWeatherItemModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ToDoListViewModel @Inject constructor(
  private val toDoRepository: ToDoRepository,
  private val weatherRepository: WeatherRepository,
  private val settingRepository: SettingRepository
) : ViewModel() {
  companion object {
    // 5分に1回天気APIをコールする
    private const val FETCH_WEATHER_DELAY_TIME_MILLIS = 300000L
  }

  private val _state = MutableLiveData(
    ToDoListState()
  )
  val state: LiveData<ToDoListState> = _state

  private val _event = MutableSharedFlow<ToDoListEvent>()
  val event: SharedFlow<ToDoListEvent> get() = _event

  private val handler = Handler(Looper.getMainLooper())
  private val fetchWeatherRunnable = Runnable {
    run {
      fetchWeather()
    }
  }

  fun onCreateView(context: Context) {
    load(context)
  }

  fun onRefresh(context: Context) {
    load(context)
  }

  private fun load(context: Context) {
    viewModelScope.launch {
      try {
        val todoList = toDoRepository.getAll()
        _state.value = _state.value?.copy(
          todoList = todoList.map {
            ToDoListItemModel.fromContent(it)
          }
        )
        Timber.tag("todolist").d(todoList.size.toString())
      } catch (e: Throwable) {
        Timber.tag("todolist").e(e)
      }
    }
    setUpWeatherApiSetting(context)
    handler.removeCallbacks(fetchWeatherRunnable)
    handler.post(fetchWeatherRunnable)
  }

  fun onClickToDoListItem(model: ToDoListItemModel) {
    val toDoContent = model.toContent()
    viewModelScope.launch {
      _event.emit(ToDoListEvent.OpenEditToDo(toDoContent))
    }
  }

  fun onClickToDoListDone(model: ToDoListItemModel) {
    val toDoContent = model.toContent()
    viewModelScope.launch {
      try {
        toDoRepository.save(toDoContent)
      } catch (e: Throwable) {
        Timber.e(e)
      }
    }
  }

  fun onClickAddToDo() {
    viewModelScope.launch {
      _event.emit(ToDoListEvent.OpenInsertToDo)
    }
  }

  private fun ToDoListItemModel.toContent() = ToDoContent(
    id = id,
    title = title,
    description = description,
    dateTime = dateTime,
    isDone = isDone
  )

  private fun setUpWeatherApiSetting(context: Context) {
    val appId = settingRepository.getOpenWeatherAppId() ?: return
    if (appId.isEmpty) {
      return
    }
    val zipCode = settingRepository.getZipCode() ?: return
    val geolocation = loadGeoLocation(zipCode = zipCode, context = context) ?: return
    _state.value = _state.value?.copy(
      openWeatherAppId = appId,
      geoLocation = geolocation
    )
  }

  private fun loadFinished() {
    _state.value = _state.value?.copy(
      refreshState = RefreshState.IDLE
    )
  }

  private fun fetchWeather() {
    val appId = _state.value?.openWeatherAppId
    if (appId == null || appId.isEmpty) {
      loadFinished()
      return
    }
    val geolocation = _state.value?.geoLocation
    if (geolocation == null) {
      loadFinished()
      return
    }

    viewModelScope.launch {
      try {
        val weatherList = weatherRepository.getWeeklyWeather(
          appId = appId,
          location = geolocation
        )
        handleWeather(weatherList = weatherList, geolocation = geolocation)
        handler.postDelayed(fetchWeatherRunnable, FETCH_WEATHER_DELAY_TIME_MILLIS)
        loadFinished()
      } catch (e: Throwable) {
        Timber.e(e)
        loadFinished()
      }
    }
  }

  private fun loadGeoLocation(zipCode: ZipCode, context: Context): Geolocation? {
    if (!zipCode.isValidZipCode) {
      return null
    }
    val geocoder = Geocoder(context, Locale.JAPAN)
    val addressList = geocoder.getFromLocationName(zipCode.value, 1)
    if (addressList != null && addressList.size != 0) {
      val address = addressList.first()
      return Geolocation(
        latitude = Latitude(address.latitude),
        longitude = Longitude(address.longitude),
        localeName = "${address.adminArea}${address.locality}"
      )
    }
    return null
  }

  private fun handleWeather(weatherList: List<Weather>, geolocation: Geolocation) {
    val todayWeather = weatherList.first()
    val weatherItemModel = ToDoListWeatherItemModel.fromWeather(
      weather = todayWeather,
      localeName = geolocation.localeName
    )
    val weeklyWeatherItemModel = ToDoListWeeklyWeatherItemModel.from(
      weatherList = weatherList,
      localeName = geolocation.localeName
    )

    val weatherState = _state.value?.weatherState
    _state.value = _state.value?.copy(
      weatherState = weatherState?.copy(
        weatherItemModel = weatherItemModel,
        weeklyWeatherItemModel = weeklyWeatherItemModel
      )
    )
  }

  fun onClickWeather() {
    val weeklyWeatherItemModel = _state.value?.weatherState?.weeklyWeatherItemModel ?: return
    viewModelScope.launch {
      _event.emit(ToDoListEvent.PresentWeeklyWeatherBottomSheet(weeklyWeatherItemModel))
    }
  }
}
