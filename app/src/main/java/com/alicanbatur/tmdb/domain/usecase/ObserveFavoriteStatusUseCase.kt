package com.alicanbatur.tmdb.domain.usecase

import com.alicanbatur.tmdb.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoriteStatusUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(movieId: Int): Flow<Boolean> = repository.observeIsFavorite(movieId)
}
