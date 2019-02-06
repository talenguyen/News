package com.besimplify.news

import android.app.Application
import android.content.Context
import com.besimplify.news.initializer.AppInitializer
import javax.inject.Inject

class App : Application() {
  companion object {
    fun getAppComponent(context: Context): AppComponent = (context.applicationContext as App).appComponent
  }

  internal lateinit var appComponent: AppComponent

  @Inject lateinit var appInitializers: Set<@JvmSuppressWildcards AppInitializer>

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.builder()
      .applicationContext(this)
      .build()

    appComponent.inject(this)

    appInitializers.forEach { it.init() }
  }
}
