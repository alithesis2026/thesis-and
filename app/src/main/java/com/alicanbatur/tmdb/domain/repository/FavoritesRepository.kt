package com.alicanbatur.tmdb.domain.repository

import com.alicanbatur.tmdb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun observeFavorites(): Flow<List<Movie>>
    fun observeIsFavorite(movieId: Int): Flow<Boolean>
    suspend fun toggleFavorite(movie: Movie, isCurrentlyFavorite: Boolean)
    suspend fun clearAll()
}
