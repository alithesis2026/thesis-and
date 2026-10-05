package com.alicanbatur.tmdb.presentation.navigation

sealed class Destination(val route: String) {
    data object MovieList : Destination("movie_list")
    data object Search : Destination("search")
    data object Favorites : Destination("favorites")
    data object Settings : Destination("settings")
    data object MovieDetail : Destination("movie_detail/{movieId}") {
        const val ARG_MOVIE_ID = "movieId"
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
}
