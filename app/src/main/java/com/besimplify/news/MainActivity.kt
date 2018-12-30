package com.besimplify.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.besimplify.news.listing.ListingFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, ListingFragment())
        .commit()
    }
  }
}
