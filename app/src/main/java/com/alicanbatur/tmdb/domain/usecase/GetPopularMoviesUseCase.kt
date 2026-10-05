package com.alicanbatur.tmdb.domain.usecase

import com.alicanbatur.tmdb.domain.model.MoviePage
import com.alicanbatur.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): Result<MoviePage> = repository.getPopularMovies(page)
}
