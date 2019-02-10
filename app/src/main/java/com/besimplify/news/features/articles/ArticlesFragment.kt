package com.besimplify.news.features.articles

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyRecyclerView
import com.besimplify.news.R
import com.besimplify.news.extensions.withModels
import com.besimplify.news.getAppComponent
import com.besimplify.news.network.NewsServices
import com.besimplify.news.view.articleView
import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticlesFragment : Fragment() {

  @Inject
  lateinit var newsServices: NewsServices
  @Inject
  lateinit var simpleChromeCustomTabs: SimpleChromeCustomTabs

  private val subs = CompositeDisposable()
  private lateinit var articlesRecyclerView: EpoxyRecyclerView

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

  @Suppress("RedundantLambdaArrow")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getAppComponent().inject(this)

    subs.add(newsServices.topHeadlines()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        {
          articlesRecyclerView.withModels {
            it.articles
              .filter { articleResponse -> articleResponse.title != null && articleResponse.url != null }
              .forEach {
                articleView {
                  id(it.url)
                  title(it.title.orEmpty())
                  source(it.source?.name.orEmpty())
                  thumbnailUrl(it.urlToImage.orEmpty())
                  publishTime(it.publishedAt?.time ?: System.currentTimeMillis())
                  onClickListener { _ ->
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
                      .navigateTo(Uri.parse(it.url), requireActivity())
                  }
                }
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
