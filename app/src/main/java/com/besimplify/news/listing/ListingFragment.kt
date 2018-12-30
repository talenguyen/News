package com.besimplify.news.listing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.besimplify.news.R
import java.text.SimpleDateFormat

class ListingFragment : Fragment() {

  private lateinit var listNews: RecyclerView
  private val newsAdapter = NewsAdapter()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return layoutInflater.inflate(
      R.layout.fragment_listing,
      container,
      false
    ).apply {
      listNews = findViewById(R.id.list_news)
      listNews.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
      listNews.adapter = newsAdapter
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    newsAdapter.setNewsList(
      listOf(
        News(
          "Eonline.com",
          "Inside Angelina Jolie's Complicated Relationship With Dad Jon Voight - E! NEWS",
          "https://akns-images.eonline.com/eol_images/Entire_Site/20181128/rs_600x600-181228120039-600-john-voight-angelina-jolie-land-blood-honey.jpg?fit=around|600:467&crop=600:467;center,top&output-quality=90",
          parseTime("2018-12-29T11:00:00Z")
        ),
        News(
          "Screenrant.com",
          "Aquaman Director Doesn't Care if It Feels 'Cheesy' | ScreenRant - Screen Rant",
          "https://static0.srcdn.com/wordpress/wp-content/uploads/2018/12/Aquaman-and-Mera-Movie-Poster.jpg",
          parseTime("2018-12-29T06:44:38Z")
        )
      )
    )
  }

  private fun parseTime(timeString: String) = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(timeString).time
}