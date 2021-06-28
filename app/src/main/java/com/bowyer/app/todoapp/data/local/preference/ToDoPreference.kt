package com.bowyer.app.todoapp.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class ToDoPreference @Inject constructor(context: Context) {
  private val sp: SharedPreferences = context.getSharedPreferences("status", Context.MODE_PRIVATE)

  fun writeBoolean(key: String, value: Boolean) {
    sp.edit().putBoolean(key, value).apply()
  }

  fun getBoolean(key: String, default: Boolean): Boolean {
    return sp.getBoolean(key, default)
  }

  fun writeString(key: String, default: String) {
    sp.edit().putString(key, default).apply()
  }

  fun getStringVal(key: String, default: String): String? {
    return sp.getString(key, default)
  }

  fun writeIntVal(key: String, value: Int) {
    sp.edit().putInt(key, value).apply()
  }

  fun getIntVal(key: String, default: Int): Int {
    return sp.getInt(key, default)
  }
}
