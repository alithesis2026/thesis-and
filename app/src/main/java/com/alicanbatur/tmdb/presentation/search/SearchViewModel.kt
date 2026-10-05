package com.alicanbatur.tmdb.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alicanbatur.tmdb.domain.model.Movie
import com.alicanbatur.tmdb.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val results: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovies: SearchMoviesUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var totalPages = 1
    private var isFetching = false
    private var activeQuery = ""

    init {
        viewModelScope.launch {
            _query
                .debounce(400)
                .distinctUntilChanged()
                .collectLatest { text -> search(text) }
        }
    }

    fun onQueryChanged(text: String) {
        _query.value = text
    }

    fun loadMoreIfNeeded(currentIndex: Int) {
        val hasMorePages = currentPage < totalPages
        if (currentIndex >= _uiState.value.results.size - 5 && hasMorePages && !isFetching) {
            viewModelScope.launch { loadNextPage() }
        }
    }

    private suspend fun search(text: String) {
        val trimmed = text.trim()
        activeQuery = trimmed
        currentPage = 0
        totalPages = 1
        _uiState.update { it.copy(results = emptyList(), errorMessage = null) }
        if (trimmed.isEmpty()) return
        loadNextPage()
    }

    private suspend fun loadNextPage() {
        val query = activeQuery
        val hasMorePages = currentPage < totalPages
        if (isFetching || query.isEmpty() || (currentPage != 0 && !hasMorePages)) return
        isFetching = true
        _uiState.update { it.copy(isLoading = currentPage == 0) }

        searchMovies(query, currentPage + 1)
            .onSuccess { page ->
                if (query == activeQuery) {
                    currentPage = page.page
                    totalPages = page.totalPages
                    val combinedResults = _uiState.value.results + page.movies
                    if (combinedResults.isEmpty()) {
                        handleEmptyResult()
                    } else {
                        _uiState.update { it.copy(results = combinedResults, isLoading = false) }
                    }
                }
            }
            .onFailure { error ->
                if (query == activeQuery) {
                    handleError(error.message ?: "Beklenmeyen bir hata oluştu.")
                }
            }
        isFetching = false
    }

    private fun handleEmptyResult() {
        _uiState.update { it.copy(isLoading = false, errorMessage = "Sonuç bulunamadı") }
    }

    private fun handleError(message: String) {
        _uiState.update { it.copy(isLoading = false, errorMessage = message) }
    }
}
