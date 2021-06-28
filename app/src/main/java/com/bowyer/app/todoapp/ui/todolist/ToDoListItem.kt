package com.bowyer.app.todoapp.ui.todolist

import android.view.View
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.LayoutTodoListItemBinding
import com.xwray.groupie.viewbinding.BindableItem
import javax.inject.Inject

class ToDoListItem(
  private val model: ToDoListItemModel,
  private val onClickItemListener: OnClickItemListener
) : BindableItem<LayoutTodoListItemBinding>(
  (ID_PREFIX + model).hashCode().toLong()
) {
  companion object {
    private const val ID_PREFIX = "layout_todo_list_item"
  }

  interface OnClickItemListener {
    fun onItemClick(model: ToDoListItemModel)
    fun onCheckChanged(model: ToDoListItemModel)
  }

  class Factory @Inject constructor() {
    fun create(model: ToDoListItemModel,
               onClickItemListener: OnClickItemListener) = ToDoListItem(
      model = model,
      onClickItemListener = onClickItemListener
    )
  }

  override fun bind(viewBinding: LayoutTodoListItemBinding, position: Int) {
    viewBinding.created.text = model.created
    viewBinding.title.text = model.title
    viewBinding.root.setOnClickListener {
      onClickItemListener.onItemClick(
        model = model
      )
    }
    viewBinding.defaultCheck.isChecked = model.isDone
    viewBinding.defaultCheck.setOnClickListener {
      onClickItemListener.onCheckChanged(
        model = model.copy(isDone = !model.isDone)
      )
    }
  }

  override fun getLayout() = R.layout.layout_todo_list_item

  override fun initializeViewBinding(view: View) = LayoutTodoListItemBinding.bind(view)
}
