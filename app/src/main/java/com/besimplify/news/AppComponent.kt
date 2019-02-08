package com.besimplify.news

import android.content.Context
import com.besimplify.news.features.articles.ArticlesFragment
import com.besimplify.news.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AppModule::class,
  AppModuleBinds::class,
  NetworkModule::class
])
interface AppComponent {

  fun inject(ignored: App)
  fun inject(ignored: ArticlesFragment)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun applicationContext(applicationContext: Context): Builder

    fun build(): AppComponent
  }
}
