package com.alicanbatur.tmdb.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviePageDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String?
)

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String?,
    val runtime: Int?,
    val genres: List<GenreDto>,
    val tagline: String?
)

data class GenreDto(
    val id: Int,
    val name: String
)
