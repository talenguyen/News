package com.besimplify.news.network.response

import com.squareup.moshi.Json
import java.util.Date

class ArticleResponse(
  @Json(name = "title") val title: String?,
  @Json(name = "source") val source: SourceResponse?,
  @Json(name = "publishedAt") val publishedAt: Date?,
  @Json(name = "urlToImage") val urlToImage: String?,
  @Json(name = "url") val url: String?
)
