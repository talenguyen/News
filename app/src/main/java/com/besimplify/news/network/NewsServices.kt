package com.besimplify.news.network

import android.content.Context
import com.besimplify.news.BuildConfig
import com.besimplify.news.extensions.isConnected
import com.besimplify.news.network.response.ArticlesResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/"

interface NewsServices {

  @Headers(
    value = [
      "Accept: application/json;charset=utf-8",
      "x-api-key: ${BuildConfig.NEWS_API_KEY}"
    ]
  )
  @GET("v2/top-headlines")
  fun topHeadlines(
    @Query("country") query: String = "us",
    @Query("page") page: Int = 1,
    @Query("limit") limit: Int = 24,
    @Query("aggregations") aggregations: Int = 1
  ): Observable<ArticlesResponse>

  companion object {

    fun make(application: Context): NewsServices {
      val cacheSize = (5 * 1024 * 1024).toLong()
      val cache = Cache(application.cacheDir, cacheSize)

      // Source https://medium.com/mindorks/caching-with-retrofit-store-responses-offline-71439ed32fda
      val okHttpClient = OkHttpClient.Builder()
        // Specify the cache we created earlier.
        .cache(cache)
        // Add an Interceptor to the OkHttpClient.
        .addInterceptor { chain ->

          // Get the request from the chain.
          var request = chain.request()

          /**  Leveraging the advantage of using Kotlin,
           *  we initialize the request and change its header depending on whether
           *  the device is connected to Internet or not.
           */
          request = if (application.isConnected())
          /**  If there is Internet, get the cache that was stored 5 seconds ago.
           *  If the cache is older than 5 seconds, then discard it,
           *  and indicate an error in fetching the response.
           *  The 'max-age' attribute is responsible for this behavior.
           */
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
          else
          /** If there is no Internet, get the cache that was stored 7 days ago.
           *  If the cache is older than 7 days, then discard it,
           *  and indicate an error in fetching the response.
           *  The 'max-stale' attribute is responsible for this behavior.
           *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
           */
            request.newBuilder().header(
              "Cache-Control",
              "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
            ).build()
          // End of if-else statement

          // Add the modified request to the chain.
          chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY))
        .build()

      val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateAdapter())
        .build()
      val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

      return retrofit.create(NewsServices::class.java)
    }
  }
}
