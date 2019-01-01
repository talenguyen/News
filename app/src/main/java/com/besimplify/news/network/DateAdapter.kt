package com.besimplify.news.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateAdapter(pattern: String = "yyyy-MM-dd'T'HH:mm:ss") {

  private val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())

  @ToJson
  fun toJson(date: Date): String = dateFormat.format(date)

  @FromJson
  fun fromJson(json: String): Date = dateFormat.parse(json)
}
