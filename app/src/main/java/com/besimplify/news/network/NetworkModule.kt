package com.besimplify.news.network

import android.content.Context
import com.besimplify.news.network.interceptor.CacheInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://newsapi.org/"

@Module
object NetworkModule {

  @Provides
  @Singleton
  @JvmStatic
  fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateAdapter())
    .build()

  @Provides
  @Singleton
  @JvmStatic
  fun provideOkHttpClient(context: Context, apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient = OkHttpClient.Builder()
    .cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong())) // Cache size 5Mb
    // Add an Interceptor to the OkHttpClient.
    .addInterceptor(CacheInterceptor(context))
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .addInterceptor(apiKeyInterceptor)
    .build()

  @Provides
  @Singleton
  @JvmStatic
  fun provideNewsServices(okHttpClient: OkHttpClient, moshi: Moshi): NewsServices = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(NewsServices::class.java)
}
