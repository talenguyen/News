package com.besimplify.news.initializer

import android.content.Context
import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs
import javax.inject.Inject

class ChromeCustomTabInitializer @Inject constructor(private val context: Context) : AppInitializer {

  override fun init() {
    SimpleChromeCustomTabs.initialize(context)
  }
}
