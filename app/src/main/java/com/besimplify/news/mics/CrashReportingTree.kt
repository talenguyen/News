package com.besimplify.news.mics

import android.util.Log
import android.util.Log.INFO
import timber.log.Timber

/**
 * A tree which logs important information for crash reporting.
 */
class CrashReportingTree : Timber.Tree() {

  override fun isLoggable(tag: String?, priority: Int): Boolean {
    return priority >= INFO
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (priority == Log.VERBOSE || priority == Log.DEBUG) {
      return
    }

    FakeCrashLibrary.log(priority, tag, message)

    if (t != null) {
      if (priority == Log.ERROR) {
        FakeCrashLibrary.logError(t)
      } else if (priority == Log.WARN) {
        FakeCrashLibrary.logWarning(t)
      }
    }
  }
}