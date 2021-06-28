package com.bowyer.app.todoapp.ui.setting

import android.view.View
import androidx.annotation.StringRes
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.LayoutSettingTitleBinding
import com.xwray.groupie.viewbinding.BindableItem
import javax.inject.Inject

class SettingTitleItem(
  @StringRes private val title: Int
) : BindableItem<LayoutSettingTitleBinding>(
  (ID_PREFIX + title).hashCode().toLong()
) {
  companion object {
    private const val ID_PREFIX = "layout_setting_title"
  }

  class Factory @Inject constructor() {
    fun create(@StringRes title: Int) = SettingTitleItem(title = title)
  }

  override fun bind(viewBinding: LayoutSettingTitleBinding, position: Int) {
    val context = viewBinding.root.context
    viewBinding.title.text = context.getString(title)
  }

  override fun getLayout() = R.layout.layout_setting_title

  override fun initializeViewBinding(view: View) = LayoutSettingTitleBinding.bind(view)
}
