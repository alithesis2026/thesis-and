package com.alicanbatur.tmdb.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alicanbatur.tmdb.data.repository.MovieRepositoryImpl
import com.alicanbatur.tmdb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieListUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var totalPages = 1
    private var isFetching = false

    init {
        loadNextPage()
    }

    fun loadMoreIfNeeded(currentIndex: Int) {
        val hasMorePages = currentPage < totalPages
        if (currentIndex >= _uiState.value.movies.size - 5 && hasMorePages && !isFetching) {
            loadNextPage()
        }
    }

    fun refresh() {
        currentPage = 0
        totalPages = 1
        _uiState.update { it.copy(movies = emptyList()) }
        loadNextPage()
    }

    private fun loadNextPage() {
        val hasMorePages = currentPage < totalPages
        if (isFetching || (currentPage != 0 && !hasMorePages)) return
        isFetching = true
        _uiState.update { it.copy(isLoading = currentPage == 0, errorMessage = null) }

        viewModelScope.launch {
            movieRepository.getPopularMovies(currentPage + 1)
                .onSuccess { page ->
                    currentPage = page.page
                    totalPages = page.totalPages
                    _uiState.update { it.copy(movies = it.movies + page.movies, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "Beklenmeyen bir hata oluştu.")
                    }
                }
            isFetching = false
        }
    }
}
