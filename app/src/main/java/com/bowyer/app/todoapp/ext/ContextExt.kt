package com.bowyer.app.todoapp.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToast(@StringRes message: Int) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
