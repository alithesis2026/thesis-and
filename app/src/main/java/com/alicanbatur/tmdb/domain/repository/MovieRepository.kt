package com.alicanbatur.tmdb.domain.repository

import com.alicanbatur.tmdb.domain.model.MovieDetail
import com.alicanbatur.tmdb.domain.model.MoviePage

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): Result<MoviePage>
    suspend fun getMovieDetail(id: Int): Result<MovieDetail>
    suspend fun searchMovies(query: String, page: Int): Result<MoviePage>
}
