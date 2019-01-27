package com.besimplify.news

import android.app.Application
import android.content.Context

class App : Application() {
  companion object {
    fun getAppComponent(context: Context): AppComponent = (context.applicationContext as App).appComponent
  }

  private lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.builder()
      .applicationContext(this)
      .build()
  }
}
