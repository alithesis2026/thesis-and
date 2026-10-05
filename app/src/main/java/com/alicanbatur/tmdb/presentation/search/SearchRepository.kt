package com.alicanbatur.tmdb.presentation.search

import com.alicanbatur.tmdb.data.remote.TMDBApiService
import com.alicanbatur.tmdb.data.remote.dto.MoviePageDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchRepository {
    private val apiService: TMDBApiService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBApiService::class.java)

    suspend fun quickSearch(query: String): MoviePageDto = apiService.searchMovies(query, 1)
}
