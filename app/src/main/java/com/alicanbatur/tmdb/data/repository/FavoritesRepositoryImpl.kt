package com.alicanbatur.tmdb.data.repository

import com.alicanbatur.tmdb.data.local.FavoriteMovieDao
import com.alicanbatur.tmdb.data.local.FavoriteMovieEntity
import com.alicanbatur.tmdb.domain.model.Movie
import com.alicanbatur.tmdb.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDao
) : FavoritesRepository {

    override fun observeFavorites(): Flow<List<Movie>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override fun observeIsFavorite(movieId: Int): Flow<Boolean> = dao.observeIsFavorite(movieId)

    override suspend fun toggleFavorite(movie: Movie, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite) {
            dao.deleteById(movie.id)
        } else {
            dao.insert(
                FavoriteMovieEntity(
                    id = movie.id,
                    title = movie.title,
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage,
                    releaseDate = movie.releaseDate,
                    addedAt = System.currentTimeMillis()
                )
            )
        }
    }

    override suspend fun clearAll() {
        dao.deleteAll()
    }
}

private fun FavoriteMovieEntity.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        overview = "",
        posterPath = posterPath,
        backdropPath = null,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
