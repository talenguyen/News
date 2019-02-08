package com.besimplify.news.features.articles

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.besimplify.news.R
import com.besimplify.news.extensions.withModels
import com.besimplify.news.getAppComponent
import com.besimplify.news.network.NewsServices
import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ArticlesFragment : Fragment() {

  @Inject
  lateinit var newsServices: NewsServices
  @Inject
  lateinit var simpleChromeCustomTabs: SimpleChromeCustomTabs

  private val subs = CompositeDisposable()
  private lateinit var articlesRecyclerView: EpoxyRecyclerView
  private val newsAdapter = ArticleAdapter { url ->
    simpleChromeCustomTabs
      .withIntentCustomizer { builder ->
        builder
          .withToolbarColor(
            ContextCompat.getColor(
              requireContext(),
              R.color.colorPrimary
            )
          )
          .withUrlBarHiding()
      }
      .navigateTo(Uri.parse(url), requireActivity())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return layoutInflater.inflate(
      R.layout.fragment_listing,
      container,
      false
    ).apply {
      articlesRecyclerView = findViewById(R.id.list_news)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getAppComponent().inject(this)

    Timber.d("onViewCreated")
    subs.add(newsServices.topHeadlines()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        {
          articlesRecyclerView.withModels {
            it.articles
              .filter { articleResponse -> articleResponse.title != null && articleResponse.url != null }
              .forEach {
              }
          }
        },
        { t: Throwable? -> t?.printStackTrace() }
      ))
  }

  override fun onDestroy() {
    super.onDestroy()
    subs.clear()
  }
}
