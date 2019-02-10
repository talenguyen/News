package com.besimplify.news

import android.content.Context
import androidx.fragment.app.Fragment
import com.besimplify.news.features.articles.ArticlesFragment
import com.besimplify.news.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AppModule::class,
    AppModuleBinds::class,
    NetworkModule::class
  ]
)
interface AppComponent {

  fun inject(ignored: App)
  fun inject(ignored: MainActivity)
  fun inject(ignored: ArticlesFragment)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun applicationContext(applicationContext: Context): Builder

    fun build(): AppComponent
  }
}

fun Context.getAppComponent(): AppComponent = App.getAppComponent(this)

fun Fragment.getAppComponent(): AppComponent = App.getAppComponent(requireContext())
