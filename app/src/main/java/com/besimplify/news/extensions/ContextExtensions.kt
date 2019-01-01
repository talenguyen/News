package com.besimplify.news.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun Context.dip(size: Int): Int = (resources.displayMetrics.density * size).toInt()

fun Context.isConnected(): Boolean {
  val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
  return activeNetwork?.isConnectedOrConnecting == true
}
