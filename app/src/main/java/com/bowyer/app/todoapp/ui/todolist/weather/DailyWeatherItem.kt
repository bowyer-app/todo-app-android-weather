package com.bowyer.app.todoapp.ui.todolist.weather

import android.view.View
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.LayoutDailyWeatherListItemBinding
import com.bowyer.app.todoapp.di.GlideApp
import com.bowyer.app.todoapp.ext.toIconRes
import com.bowyer.app.todoapp.ext.yyyyMMddEEFromUnixTime
import com.xwray.groupie.viewbinding.BindableItem
import javax.inject.Inject

class DailyWeatherItem(
  private val model: ToDoListWeatherItemModel
) : BindableItem<LayoutDailyWeatherListItemBinding>(
  (ID_PREFIX + model).hashCode().toLong()
) {
  companion object {
    private const val ID_PREFIX = "layout_daily_weather_list_item"
  }

  class Factory @Inject constructor() {
    fun create(model: ToDoListWeatherItemModel) =
      DailyWeatherItem(model = model)
  }

  override fun bind(viewBinding: LayoutDailyWeatherListItemBinding, position: Int) {
    val context = viewBinding.root.context
    viewBinding.date.text = model.dateTime.yyyyMMddEEFromUnixTime()
    viewBinding.description.text = model.description
    viewBinding.maxTemperature.text = context.getString(R.string.temperature,
      model.maxTemperature)
    viewBinding.minTemperature.text = context.getString(R.string.temperature,
      model.minTemperature)

    GlideApp.with(viewBinding.weatherIcon)
      .load(model.weatherType.toIconRes())
      .into(viewBinding.weatherIcon)
  }

  override fun getLayout() = R.layout.layout_daily_weather_list_item

  override fun initializeViewBinding(view: View): LayoutDailyWeatherListItemBinding =
    LayoutDailyWeatherListItemBinding.bind(view)
}
