package com.alicanbatur.tmdb.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alicanbatur.tmdb.domain.model.Movie
import com.alicanbatur.tmdb.domain.repository.FavoritesRepository
import com.alicanbatur.tmdb.domain.usecase.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavorites: GetFavoritesUseCase,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    val favorites: StateFlow<List<Movie>> = getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun clearAllFavorites() {
        viewModelScope.launch {
            favoritesRepository.clearAll()
        }
    }
}
