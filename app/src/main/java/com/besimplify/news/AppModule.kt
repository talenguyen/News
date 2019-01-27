package com.besimplify.news

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object AppModule {

  @JvmStatic
  @Provides
  @Named("NEWS_API_KEY")
  fun provideApiKey(): String = BuildConfig.NEWS_API_KEY
}
