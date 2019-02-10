package com.besimplify.news

import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object AppModule {

  @JvmStatic
  @Provides
  @Named("NEWS_API_KEY")
  fun provideApiKey(): String = BuildConfig.NEWS_API_KEY

  @JvmStatic
  @Provides
  @Named("DEBUG")
  fun provideDebugFlag(): Boolean = BuildConfig.DEBUG

  @Provides
  @Singleton
  @JvmStatic
  fun provideSimpleChromeCustomTabs(): SimpleChromeCustomTabs = SimpleChromeCustomTabs.getInstance()
}
