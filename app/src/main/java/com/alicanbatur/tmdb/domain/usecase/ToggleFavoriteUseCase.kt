package com.alicanbatur.tmdb.domain.usecase

import com.alicanbatur.tmdb.domain.model.Movie
import com.alicanbatur.tmdb.domain.repository.FavoritesRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(movie: Movie, isCurrentlyFavorite: Boolean) =
        repository.toggleFavorite(movie, isCurrentlyFavorite)
}
