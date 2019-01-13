package com.besimplify.news.listing

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.besimplify.news.R
import com.squareup.picasso.Picasso
import java.util.Date
import java.util.concurrent.TimeUnit

data class Article(
  val source: String,
  val title: String,
  val thumbUrl: String,
  val url: String,
  val publishAt: Date
)

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val thumb: ImageView = view.findViewById(R.id.thumb)
  val source: TextView = view.findViewById(R.id.source)
  val title: TextView = view.findViewById(R.id.title)
  val publishAt: TextView = view.findViewById(R.id.publish_at)
}

class ArticleAdapter(private val navigator: (String) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>() {

  private var articles: List<Article> = emptyList()

  fun setArticles(articleList: List<Article>) {
    this.articles = articleList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    return ArticleViewHolder(view)
  }

  override fun getItemCount(): Int {
    return articles.size
  }

  override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
    val article = articles[position]

    holder.itemView.setOnClickListener { navigator.invoke(article.url) }

    holder.source.text = article.source
    holder.title.text = article.title

    val requestCreator = if (article.thumbUrl.isEmpty()) {
      Picasso.get()
        .load(R.drawable.ic_image_black_24dp)
    } else {
      Picasso.get()
        .load(article.thumbUrl)
    }

    requestCreator
      .fit()
      .centerCrop()
      .into(holder.thumb)

    holder.publishAt.text = formatPublishAt(holder.publishAt.context, article.publishAt)
  }

  private fun formatPublishAt(context: Context, publishAt: Date): String {
    val diffMillis = Date().time - publishAt.time

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
