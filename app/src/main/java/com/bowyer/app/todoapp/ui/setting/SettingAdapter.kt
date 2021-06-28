package com.bowyer.app.todoapp.ui.setting

import com.bowyer.app.todoapp.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import javax.inject.Inject

class SettingAdapter @Inject constructor(
  private val settingTitleFactory: SettingTitleItem.Factory,
  private val settingItemFactory: SettingItem.Factory
) : GroupAdapter<GroupieViewHolder>() {
  private val apiSettingSection = Section()

  init {
    add(apiSettingSection)
  }

  fun updateApiSetting(
    list: List<SettingItemModel>,
    onClickItem: (settingItemModel: SettingItemModel) -> Unit
  ) {
    val items = mutableListOf<Item<*>>()
    items.add(
      settingTitleFactory.create(R.string.api_setting)
    )
    list.map { model ->
      // FIXME ここきれいにする
      if (model is SettingItemModel.LicenseSetting) {
        items.add(
          settingTitleFactory.create(R.string.other_setting)
        )
        items.add(
          settingItemFactory.create(
            model = model,
            onClick = {
              onClickItem(model)
            }
          )
        )
      } else {
        items.add(
          settingItemFactory.create(
            model = model,
            onClick = {
              onClickItem(model)
            }
          )
        )
      }
    }
    apiSettingSection.update(items)
  }
}
