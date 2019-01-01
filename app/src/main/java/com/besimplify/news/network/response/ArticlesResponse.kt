package com.besimplify.news.network.response

import com.squareup.moshi.Json

class ArticlesResponse(
  @Json(name = "status")
  val status: String = "error",
  @Json(name = "totalResults")
  val total: Int = 0,
  @Json(name = "articles")
  val articles: List<ArticleResponse> = emptyList()
)