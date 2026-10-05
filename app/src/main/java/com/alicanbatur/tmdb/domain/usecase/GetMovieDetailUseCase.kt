package com.alicanbatur.tmdb.domain.usecase

import com.alicanbatur.tmdb.domain.model.MovieDetail
import com.alicanbatur.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int): Result<MovieDetail> = repository.getMovieDetail(id)
}
