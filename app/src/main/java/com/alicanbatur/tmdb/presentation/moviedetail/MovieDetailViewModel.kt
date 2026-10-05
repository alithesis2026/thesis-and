package com.alicanbatur.tmdb.presentation.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alicanbatur.tmdb.domain.model.Movie
import com.alicanbatur.tmdb.domain.model.MovieDetail
import com.alicanbatur.tmdb.domain.usecase.GetMovieDetailUseCase
import com.alicanbatur.tmdb.domain.usecase.ObserveFavoriteStatusUseCase
import com.alicanbatur.tmdb.domain.usecase.ToggleFavoriteUseCase
import com.alicanbatur.tmdb.presentation.navigation.Destination
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieDetailUiState(
    val detail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFavorite: Boolean = false
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetail: GetMovieDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    observeFavoriteStatus: ObserveFavoriteStatusUseCase
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle[Destination.MovieDetail.ARG_MOVIE_ID])
    val gson = Gson()

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        load()
        viewModelScope.launch {
            observeFavoriteStatus(movieId).collect { isFavorite ->
                _uiState.update { it.copy(isFavorite = isFavorite) }
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            getMovieDetail(movieId)
                .onSuccess { detail ->
                    android.util.Log.d("MovieDetailViewModel", gson.toJson(detail))
                    _uiState.update { it.copy(detail = detail, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "Beklenmeyen bir hata oluştu.")
                    }
                }
        }
    }

    fun toggleFavorite() {
        val detail = _uiState.value.detail ?: return
        val movie = Movie(
            id = detail.id,
            title = detail.title,
            overview = detail.overview,
            posterPath = detail.posterPath,
            backdropPath = detail.backdropPath,
            voteAverage = detail.voteAverage,
            releaseDate = detail.releaseDate
        )
        viewModelScope.launch {
            toggleFavoriteUseCase(movie, _uiState.value.isFavorite)
        }
    }
}
