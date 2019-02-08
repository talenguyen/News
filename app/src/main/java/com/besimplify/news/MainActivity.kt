package com.besimplify.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.besimplify.news.features.articles.ArticlesFragment
import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs

class MainActivity : AppCompatActivity() {

  private val chromeCustomTabs: SimpleChromeCustomTabs by lazy { SimpleChromeCustomTabs.getInstance() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    SimpleChromeCustomTabs.initialize(this)

    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, ArticlesFragment())
        .commit()
    }
  }

  override fun onResume() {
    super.onResume()
    chromeCustomTabs.connectTo(this)
  }

  override fun onPause() {
    chromeCustomTabs.disconnectFrom(this)
    super.onPause()
  }
}
