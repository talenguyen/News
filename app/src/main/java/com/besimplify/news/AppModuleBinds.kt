package com.besimplify.news

import com.besimplify.news.initializer.AppInitializer
import com.besimplify.news.initializer.ChromeCustomTabInitializer
import com.besimplify.news.initializer.TimberInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class AppModuleBinds {

  @Binds
  @IntoSet
  abstract fun provideTimberInitializer(initializer: TimberInitializer): AppInitializer

  @Binds
  @IntoSet
  abstract fun provideChromeCustomTabInitializer(initializer: ChromeCustomTabInitializer): AppInitializer
}
