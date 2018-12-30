package com.besimplify.news.listing

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.besimplify.news.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date

data class News(
  val source: String,
  val title: String,
  val thumbUrl: String,
  val publishAt: Long
)

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val thumb: ImageView = view.findViewById(R.id.thumb)
  val source: TextView = view.findViewById(R.id.source)
  val title: TextView = view.findViewById(R.id.title)
  val publishAt: TextView = view.findViewById(R.id.publish_at)
}

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {
  private var newsList: List<News> = emptyList()

  fun setNewsList(newsList: List<News>) {
    this.newsList = newsList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    return NewsViewHolder(view)
  }

  override fun getItemCount(): Int {
    return newsList.size
  }

  override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    val news = newsList[position]
    holder.source.text = news.source
    holder.title.text = news.title
    Picasso.get()
      .load(news.thumbUrl)
      .fit()
      .centerCrop()
      .into(holder.thumb)
    holder.publishAt.text = computePublishAt(news.publishAt)
  }

  private fun computePublishAt(publishAt: Long): String {
    return SimpleDateFormat.getDateTimeInstance().format(Date(publishAt))
  }
}