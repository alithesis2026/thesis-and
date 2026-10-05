package com.alicanbatur.tmdb.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String?
)

data class MoviePage(
    val movies: List<Movie>,
    val page: Int,
    val totalPages: Int
)

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String?,
    val runtime: Int?,
    val genres: List<String>,
    val tagline: String?
)
