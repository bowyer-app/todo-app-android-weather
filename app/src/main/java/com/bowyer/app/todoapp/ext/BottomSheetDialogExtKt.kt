package com.bowyer.app.todoapp.ext

import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun BottomSheetDialog.setSheetState(state: Int) {
  val bottomSheetView =
    findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
  bottomSheetView?.let {
    BottomSheetBehavior.from(bottomSheetView).state = state
  }
}
