package com.bowyer.app.todoapp.ui.todolist.weather

import android.view.View
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.LayoutTodoListWeatherItemBinding
import com.bowyer.app.todoapp.di.GlideApp
import com.bowyer.app.todoapp.ext.toIconRes
import com.xwray.groupie.viewbinding.BindableItem
import javax.inject.Inject

class ToDoListWeatherItem(
  val model: ToDoListWeatherItemModel,
  val onClickItem: () -> Unit
) : BindableItem<LayoutTodoListWeatherItemBinding>(
  (ID_PREFIX + model).hashCode().toLong()
) {
  companion object {
    private const val ID_PREFIX = "layout_todo_list_weather_item"
  }

  class Factory @Inject constructor() {
    fun create(model: ToDoListWeatherItemModel, onClickItem: () -> Unit) =
      ToDoListWeatherItem(
        model = model,
        onClickItem = onClickItem
      )
  }

  override fun bind(viewBinding: LayoutTodoListWeatherItemBinding, position: Int) {
    viewBinding.root.setOnClickListener {
      onClickItem()
    }
    val context = viewBinding.root.context
    viewBinding.locationName.text = model.localeName
    viewBinding.nowTemperature.text =
      context.getString(R.string.now_temperature, model.nowTemperature)
    viewBinding.description.text = model.description
    viewBinding.maxTemperature.text = context.getString(
      R.string.temperature,
      model.maxTemperature
    )
    viewBinding.minTemperature.text = context.getString(
      R.string.temperature,
      model.minTemperature
    )

    GlideApp.with(viewBinding.weatherIcon)
      .load(model.weatherType.toIconRes())
      .into(viewBinding.weatherIcon)
  }

  override fun getLayout() = R.layout.layout_todo_list_weather_item

  override fun initializeViewBinding(view: View) = LayoutTodoListWeatherItemBinding.bind(view)
}
