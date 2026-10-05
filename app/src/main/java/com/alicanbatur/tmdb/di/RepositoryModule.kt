package com.alicanbatur.tmdb.di

import com.alicanbatur.tmdb.data.repository.FavoritesRepositoryImpl
import com.alicanbatur.tmdb.data.repository.MovieRepositoryImpl
import com.alicanbatur.tmdb.domain.repository.FavoritesRepository
import com.alicanbatur.tmdb.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository
}
