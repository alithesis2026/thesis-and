package com.alicanbatur.tmdb.data.remote

import com.alicanbatur.tmdb.data.remote.dto.MovieDetailDto
import com.alicanbatur.tmdb.data.remote.dto.MoviePageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviePageDto

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): MovieDetailDto

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String, @Query("page") page: Int): MoviePageDto
}
