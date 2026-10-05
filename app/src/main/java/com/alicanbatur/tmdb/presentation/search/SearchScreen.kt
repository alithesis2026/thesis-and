package com.alicanbatur.tmdb.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alicanbatur.tmdb.presentation.components.MovieGridItem

@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchInputField(onSearch = viewModel::onQueryChanged)

        when {
            uiState.isLoading && uiState.results.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null && uiState.results.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.errorMessage ?: "", style = MaterialTheme.typography.bodyMedium)
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(uiState.results, key = { _, movie -> movie.id }) { index, movie ->
                        viewModel.loadMoreIfNeeded(index)
                        MovieGridItem(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchInputField(onSearch: (String) -> Unit) {
    var localQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = localQuery,
        onValueChange = { text ->
            localQuery = text
            onSearch(text)
        },
        label = { Text("Film ara") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
