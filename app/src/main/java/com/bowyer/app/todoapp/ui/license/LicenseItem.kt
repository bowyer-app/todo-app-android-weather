package com.bowyer.app.todoapp.ui.license

import android.view.View
import androidx.core.text.HtmlCompat
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.LayoutLicenseItemBinding
import com.xwray.groupie.viewbinding.BindableItem
import javax.inject.Inject

class LicenseItem(
  private val model: LicenseItemModel,
  private val onClickItem: () -> Unit
) : BindableItem<LayoutLicenseItemBinding>(
  (ID_PREFIX + model).hashCode().toLong()
) {
  companion object {
    private const val ID_PREFIX = "layout_license_item"
  }

  class Factory @Inject constructor() {
    fun create(
      model: LicenseItemModel,
      onClickItem: () -> Unit
    ) = LicenseItem(
      model = model,
      onClickItem = onClickItem
    )
  }

  override fun bind(viewBinding: LayoutLicenseItemBinding, position: Int) {
    viewBinding.root.setOnClickListener {
      onClickItem()
    }
    viewBinding.libraryName.text = model.libraryName
    viewBinding.copyrightHolder.text = model.copyrightHolder
    viewBinding.license.text = model.license
    model.url?.let { url ->
      viewBinding.url.text =
        HtmlCompat.fromHtml("<u>$url</u>", HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    }
  }

  override fun getLayout() = R.layout.layout_license_item

  override fun initializeViewBinding(view: View) = LayoutLicenseItemBinding.bind(view)

}
