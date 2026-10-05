package com.alicanbatur.tmdb.domain.usecase

import com.alicanbatur.tmdb.domain.model.MoviePage
import com.alicanbatur.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int): Result<MoviePage> =
        repository.searchMovies(query, page)
}
