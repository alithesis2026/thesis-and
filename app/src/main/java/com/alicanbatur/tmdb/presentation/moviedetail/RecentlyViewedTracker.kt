package com.alicanbatur.tmdb.presentation.moviedetail

import android.content.Context
import android.util.Log

class RecentlyViewedTracker(private val context: Context) {

    companion object {
        @Volatile private var instance: RecentlyViewedTracker? = null

        fun getInstance(context: Context): RecentlyViewedTracker =
            instance ?: synchronized(this) {
                instance ?: RecentlyViewedTracker(context).also { instance = it }
            }
    }

    fun track(movieId: Int) {
        Log.d("RecentlyViewedTracker", "Viewed $movieId, context=$context")
    }
}
