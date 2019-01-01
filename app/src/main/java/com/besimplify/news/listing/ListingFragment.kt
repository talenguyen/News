package com.besimplify.news.listing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.besimplify.news.R
import com.besimplify.news.network.NewsServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListingFragment : Fragment() {

  private val newsServices by lazy { NewsServices.make(requireContext()) }
  private val subs = CompositeDisposable()

  private lateinit var listNews: RecyclerView
  private val newsAdapter = ArticleAdapter()

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
    subs.add(newsServices.topHeadlines()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        {
          val newsList = it.articles
            .map { articleResponse ->
              Article(
                articleResponse.source.name,
                articleResponse.title,
                articleResponse.urlToImage ?: "",
                articleResponse.publishedAt
              )
            }
          newsAdapter.setArticles(newsList)
        },
        { t: Throwable? -> t?.printStackTrace() }
      ))
  }

  override fun onDestroy() {
    super.onDestroy()
    subs.clear()
  }
}
