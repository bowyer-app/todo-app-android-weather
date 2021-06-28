package com.bowyer.app.todoapp.ui.setting

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.FragmentSettingBinding
import com.bowyer.app.todoapp.ext.safeNavigate
import com.bowyer.app.todoapp.ext.toEditable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {

  @Inject
  lateinit var viewModel: SettingViewModel

  @Inject
  lateinit var settingAdapter: SettingAdapter

  private lateinit var binding: FragmentSettingBinding

  private val navController: NavController by lazy { findNavController() }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSettingBinding.inflate(inflater, container, false)
    viewModel.state.observe(viewLifecycleOwner) { state ->
      state?.settingList?.let { list ->
        updateSetting(settingList = list)
      }
    }
    lifecycleScope.launch {
      viewModel.event.collect { event ->
        when (event) {
          is SettingEvent.PresentZipCodeDialog -> {
            presentEditValueDialog(
              model = event.itemModel,
              onClickSave = viewModel::onClickZipCodeSave
            )
          }
          is SettingEvent.PresentAppIdDialog -> {
            presentEditValueDialog(
              model = event.itemModel,
              onClickSave = viewModel::onClickAppIdSave
            )
          }
          is SettingEvent.PresentErrorDialog -> {
            presentAlertDialog(
              title = event.title,
              message = event.message
            )
          }
          is SettingEvent.PresentLicensePage -> {
            val action = SettingFragmentDirections.actionPresentLicense()
            navController.safeNavigate(action)
          }
        }
      }
    }
    initRecyclerView()
    viewModel.onCreateView()
    return binding.root
  }

  private fun initRecyclerView() {
    binding.recycler.apply {
      layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      adapter = settingAdapter
    }
  }

  private fun updateSetting(settingList: List<SettingItemModel>) {
    settingAdapter.updateApiSetting(
      list = settingList,
      onClickItem = { model ->
        viewModel.onClickSettingItem(model)
      }
    )
  }

  private fun presentEditValueDialog(
    model: SettingItemModel,
    onClickSave: (value: String) -> Unit
  ) {
    val editText = EditText(requireContext()).apply {
      text = model.description?.toEditable()
      hint = getString(model.hintText)
    }
    AlertDialog.Builder(
      requireContext()
    ).setTitle(model.title)
      .setView(editText)
      .setPositiveButton(
        R.string.save
      ) { _, _ ->
        onClickSave(editText.text.toString())
      }.show()
  }

  private fun presentAlertDialog(
    @StringRes title: Int,
    @StringRes message: Int
  ) {
    AlertDialog.Builder(
      requireContext()
    ).setTitle(title)
      .setMessage(message)
      .setPositiveButton(
        R.string.save
      ) { _, _ ->
        // do nothing
      }.show()
  }
}
