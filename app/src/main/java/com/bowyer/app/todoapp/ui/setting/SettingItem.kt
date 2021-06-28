package com.bowyer.app.todoapp.ui.setting

import android.view.View
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.LayoutSettingItemBinding
import com.xwray.groupie.viewbinding.BindableItem
import javax.inject.Inject

class SettingItem(
  val model: SettingItemModel,
  val onClick: () -> Unit
) : BindableItem<LayoutSettingItemBinding>(
  (ID_PREFIX + model).hashCode().toLong()
) {
  companion object {
    private const val ID_PREFIX = "layout_setting_item"
  }

  class Factory @Inject constructor() {
    fun create(
      model: SettingItemModel,
      onClick: () -> Unit
    ) = SettingItem(model = model, onClick = onClick)
  }

  override fun bind(viewBinding: LayoutSettingItemBinding, position: Int) {
    val context = viewBinding.root.context
    viewBinding.title.text = context.getString(model.title)
    model.description?.takeIf { it.isNotEmpty() }?.let { description ->
      viewBinding.description.text = description
    }
    viewBinding.root.setOnClickListener {
      onClick()
    }
  }

  override fun getLayout() = R.layout.layout_setting_item

  override fun initializeViewBinding(view: View) = LayoutSettingItemBinding.bind(view)
}
