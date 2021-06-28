package com.bowyer.app.todoapp.ui.todolist.weather

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.DialogWeeklyWeatherBottomSheetBinding
import com.bowyer.app.todoapp.ext.setSheetState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeeklyWeatherBottomSheetDialog : BottomSheetDialogFragment() {

  @Inject
  lateinit var dailyWeatherItemFactory: DailyWeatherItem.Factory
  private lateinit var binding: DialogWeeklyWeatherBottomSheetBinding
  private val section = Section()
  private val args: WeeklyWeatherBottomSheetDialogArgs by navArgs()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(DialogFragment.STYLE_NORMAL, R.style.Common_BottomSheetTheme)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)

    val inflater = LayoutInflater.from(context)
    binding = DialogWeeklyWeatherBottomSheetBinding.inflate(inflater)
    initRecycler()
    dialog.setContentView(binding.root)
    dialog.setOnShowListener {
      (it as? BottomSheetDialog)?.setSheetState(BottomSheetBehavior.STATE_EXPANDED)
    }
    return dialog
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val maxHeight = getPopupHeight(.85f)
    binding.container.maxHeight = maxHeight + 1
  }

  private fun initRecycler() {
    val dividerItemDecoration = DividerItemDecoration(
      requireContext(),
      LinearLayoutManager(requireContext()).orientation
    ).apply {
      ContextCompat.getDrawable(
        requireContext(),
        R.drawable.divider
      )?.let {
        setDrawable(
          it
        )
      }
    }
    binding.recycler.apply {
      addItemDecoration(dividerItemDecoration)
      adapter = GroupAdapter<GroupieViewHolder>().apply {
        add(section)
      }
    }
    args.weeklyWeatherItemModel.weeklyWeather.map { dailyWeather ->
      section.add(
        dailyWeatherItemFactory.create(
          model = dailyWeather
        )
      )
    }
  }

  private fun getPopupHeight(percent: Float): Int {
    val displayMetrics = DisplayMetrics()
    activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
    return (displayMetrics.heightPixels * percent).toInt()
  }
}
