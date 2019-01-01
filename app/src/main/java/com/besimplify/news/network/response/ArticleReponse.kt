package com.besimplify.news.network.response

import com.squareup.moshi.Json
import java.util.Date

class ArticleReponse(
  @Json(name = "title") val title: String,
  @Json(name = "source") val source: SourceResponse,
  @Json(name = "publishedAt") val publishedAt: Date,
  @Json(name = "urlToImage") val urlToImage: String?
)
