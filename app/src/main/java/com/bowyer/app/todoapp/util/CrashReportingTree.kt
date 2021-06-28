package com.bowyer.app.todoapp.util

import android.util.Log
import com.bowyer.app.todoapp.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber


class CrashReportingTree : Timber.Tree() {

  companion object {
    private const val PRODUCTION = "production"
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (PRODUCTION != BuildConfig.FLAVOR ||
      priority == Log.VERBOSE ||
      priority == Log.DEBUG
    ) {
      return
    }
    FirebaseCrashlytics.getInstance().log(message)

    if (t != null) {
      if (priority == Log.ERROR) {
        FirebaseCrashlytics.getInstance().recordException(t)
      }
    }
  }
}
