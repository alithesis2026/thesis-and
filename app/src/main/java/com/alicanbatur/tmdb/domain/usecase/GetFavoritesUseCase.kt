package com.alicanbatur.tmdb.domain.usecase

import com.alicanbatur.tmdb.domain.model.Movie
import com.alicanbatur.tmdb.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Movie>> = repository.observeFavorites()
}
