package com.besimplify.news.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class ApiKeyInterceptor @Inject constructor(@Named("NEWS_API_KEY") private val apiKey: String) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val newRequest = chain.request()
      .newBuilder()
      .addHeader("x-api-key", apiKey)
      .build()
    return chain.proceed(newRequest)
  }
}
