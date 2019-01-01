package com.besimplify.news.network.response

import com.squareup.moshi.Json

class SourceResponse(
  @Json(name = "name") val name: String
)