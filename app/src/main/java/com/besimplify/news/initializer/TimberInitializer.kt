package com.besimplify.news.initializer

import com.besimplify.news.mics.CrashReportingTree
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class TimberInitializer @Inject constructor(@Named("DEBUG") private val debug: Boolean) : AppInitializer {
  override fun init() {
    if (debug) {
      Timber.plant(Timber.DebugTree())
    } else {
      Timber.plant(CrashReportingTree())
    }
  }
}
