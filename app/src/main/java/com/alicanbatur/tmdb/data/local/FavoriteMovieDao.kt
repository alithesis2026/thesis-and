package com.alicanbatur.tmdb.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :movieId)")
    fun observeIsFavorite(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    suspend fun deleteById(movieId: Int)
}
