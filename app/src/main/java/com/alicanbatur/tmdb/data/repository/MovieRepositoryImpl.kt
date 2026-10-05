package com.alicanbatur.tmdb.data.repository

import com.alicanbatur.tmdb.data.remote.TMDBApiService
import com.alicanbatur.tmdb.data.remote.dto.MovieDetailDto
import com.alicanbatur.tmdb.data.remote.dto.MovieDto
import com.alicanbatur.tmdb.data.remote.dto.MoviePageDto
import com.alicanbatur.tmdb.domain.model.Movie
import com.alicanbatur.tmdb.domain.model.MovieDetail
import com.alicanbatur.tmdb.domain.model.MoviePage
import com.alicanbatur.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TMDBApiService
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Result<MoviePage> = runCatching {
        apiService.getPopularMovies(page).toDomain()
    }

    override suspend fun getMovieDetail(id: Int): Result<MovieDetail> = runCatching {
        apiService.getMovieDetail(id).toDomain()
    }

    override suspend fun searchMovies(query: String, page: Int): Result<MoviePage> = runCatching {
        apiService.searchMovies(query, page).toDomain()
    }

    override suspend fun prefetchNextPage(page: Int) {
        try {
            apiService.getPopularMovies(page)
        } catch (e: Exception) {
        }
    }
}

private fun MoviePageDto.toDomain(): MoviePage =
    MoviePage(movies = results.map { it.toDomain() }, page = page, totalPages = totalPages)

private fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )

private fun MovieDetailDto.toDomain(): MovieDetail =
    MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        runtime = runtime,
        genres = genres.map { it.name },
        tagline = tagline
    )
