package com.bowyer.app.todoapp.ui.license

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import javax.inject.Inject

class LicenseAdapter @Inject constructor(
  private val licenseItemFactory: LicenseItem.Factory
) : GroupAdapter<GroupieViewHolder>() {

  private val section = Section()

  init {
    add(section)
  }

  fun update(list: List<LicenseItemModel>, onClickItem: (model: LicenseItemModel) -> Unit) {
    val items = mutableListOf<Item<*>>()
    list.map { model ->
      items.add(
        licenseItemFactory.create(
          model = model,
          onClickItem = {
            onClickItem(model)
          }
        )
      )
    }
    section.update(items)
  }
}
