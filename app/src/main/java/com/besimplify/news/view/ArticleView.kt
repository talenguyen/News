package com.besimplify.news.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.besimplify.news.R
import com.squareup.picasso.Picasso
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.LazyThreadSafetyMode.NONE

@ModelView(
  defaultLayout = R.layout.view_article
)
class ArticleView(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
  private val thumb: ImageView by lazy(NONE) { findViewById<ImageView>(R.id.thumb) }
  private val source: TextView by lazy(NONE) { findViewById<TextView>(R.id.source) }
  private val title: TextView by lazy(NONE) { findViewById<TextView>(R.id.title) }
  private val publishAt: TextView by lazy(NONE) { findViewById<TextView>(R.id.publish_at) }

  @CallbackProp
  override fun setOnClickListener(l: OnClickListener?) {
    super.setOnClickListener(l)
  }

  @ModelProp
  fun setSource(source: String) {
    this.source.text = source
  }

  @ModelProp
  fun setTitle(title: String) {
    this.title.text = title
  }

  @ModelProp
  fun setThumbnailUrl(url: String) {
    val requestCreator = if (url.isEmpty()) {
      Picasso.get()
        .load(R.drawable.ic_image_black_24dp)
    } else {
      Picasso.get()
        .load(url)
    }

    requestCreator
      .fit()
      .centerCrop()
      .into(thumb)
  }

  @ModelProp
  fun setPublishTime(time: Long) {
    val publishTime = formatPublishTime(context, time)
    publishAt.text = publishTime
  }

  private fun formatPublishTime(context: Context, publishAt: Long): String {
    val diffMillis = Date().time - publishAt

    val oneDayInMillis = TimeUnit.DAYS.toMillis(1)
    if (diffMillis >= oneDayInMillis) {
      val diffDays: Int = (diffMillis / oneDayInMillis).toInt()
      return context.resources.getQuantityString(R.plurals.number_of_days, diffDays, diffDays)
    }

    val oneHourInMillis = TimeUnit.HOURS.toMillis(1)
    if (diffMillis >= oneHourInMillis) {
      val diffHours: Int = (diffMillis / oneHourInMillis).toInt()
      return context.resources.getQuantityString(R.plurals.number_of_hours, diffHours, diffHours)
    }

    val oneMinuteInMills = TimeUnit.MINUTES.toMillis((1))
    if (diffMillis >= oneMinuteInMills) {
      val diffMinutes: Int = (diffMillis / oneMinuteInMills).toInt()
      return context.resources.getQuantityString(R.plurals.number_of_minutes, diffMinutes, diffMinutes)
    }

    return context.getString(R.string.now)
  }
}
