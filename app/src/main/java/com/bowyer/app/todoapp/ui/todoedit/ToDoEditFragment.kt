package com.bowyer.app.todoapp.ui.todoedit

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.FragmentTodoEditBinding
import com.bowyer.app.todoapp.domain.ToDoContent
import com.bowyer.app.todoapp.ext.showToast
import com.bowyer.app.todoapp.ext.toEditable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ToDoEditFragment : Fragment() {

  @Inject
  lateinit var viewModel: ToDoEditViewModel

  private val args: ToDoEditFragmentArgs by navArgs()

  private lateinit var binding: FragmentTodoEditBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentTodoEditBinding.inflate(inflater, container, false)
    viewModel.state.observe(viewLifecycleOwner) { state ->
      restoreContent(state?.toDoContent)
    }
    lifecycleScope.launch {
      viewModel.event.collect { event ->
        when (event) {
          is ToDoEditEvent.HideKeyboard -> {
            hideKeyboard()
          }
          is ToDoEditEvent.PopBackStack -> {
            findNavController().popBackStack()
          }
          is ToDoEditEvent.PresentSaveToDoFailedDialog -> {
            showAlertDialog(R.string.message_failed_to_save)
          }
          is ToDoEditEvent.PresentShowNeedValueAlertDialog -> {
            showAlertDialog(R.string.message_need_title_and_description)
          }
          is ToDoEditEvent.PresentDeleteToDoFailed -> {
            showAlertDialog(R.string.message_failed_to_delete)
          }
          is ToDoEditEvent.PresentDeleteToDoSuccessToastPop -> {
            requireContext().showToast(R.string.message_delete_success)
            findNavController().popBackStack()
          }
        }
      }
    }
    viewModel.onCreateView(args.todo)
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    if (args.todo == null) {
      inflater.inflate(R.menu.todo_insert, menu)
    } else {
      inflater.inflate(R.menu.todo_edit, menu)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.save -> {
        viewModel.onClickSave(
          title = binding.title.text.toString(),
          description = binding.description.text.toString()
        )
        return false
      }
      R.id.delete -> {
        viewModel.onClickDelete()
        return false
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun restoreContent(content: ToDoContent?) {
    val todoContent = content ?: return
    binding.title.text = todoContent.title.toEditable()
    binding.description.text = todoContent.description.toEditable()
  }

  private fun showAlertDialog(@StringRes message: Int) {
    AlertDialog.Builder(
      requireContext()
    ).setTitle(R.string.dialog_title_error)
      .setMessage(message)
      .setPositiveButton(
        R.string.ok
      ) { _, _ ->
        // do nothing
      }.show()
  }

  private fun hideKeyboard() {
    requireActivity().currentFocus?.let { view ->
      val manager =
        requireActivity().getSystemService(
          Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
      manager.hideSoftInputFromWindow(view.windowToken, 0)
    }
  }
}
