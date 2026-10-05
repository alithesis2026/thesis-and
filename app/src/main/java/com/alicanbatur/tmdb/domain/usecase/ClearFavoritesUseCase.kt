package com.alicanbatur.tmdb.domain.usecase

import com.alicanbatur.tmdb.domain.repository.FavoritesRepository
import javax.inject.Inject

class ClearFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke() = repository.clearAll()
}
