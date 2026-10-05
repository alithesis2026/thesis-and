package com.alicanbatur.tmdb.di

import android.content.Context
import androidx.room.Room
import com.alicanbatur.tmdb.data.local.AppDatabase
import com.alicanbatur.tmdb.data.local.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "tmdb-movie-app.db").build()

    @Provides
    fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao = database.favoriteMovieDao()
}
