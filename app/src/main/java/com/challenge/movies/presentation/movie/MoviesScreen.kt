package com.challenge.movies.presentation.movie

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.challenge.movies.domain.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    uiState: MoviesScreenUiState,
    uiSideEffect: MoviesScreenUiSideEffect,
    movies: LazyPagingItems<Movie>,
    onEvent: (MoviesScreenUiEvent) -> Unit,
    navigateToDetailsScreen: (Movie) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Popular Movies") })
        },
        content = { padding ->
            val context = LocalContext.current

            LaunchedEffect(movies.loadState) {
                onEvent(MoviesScreenUiEvent.OnLoadStateChanged(movies.loadState))
            }

            LaunchedEffect(uiSideEffect) {
                if (uiSideEffect is MoviesScreenUiSideEffect.ShowError) {
                    Toast.makeText(
                        context,
                        "Error: ${uiSideEffect.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            Column {
                OutlinedTextField(
                    value = uiState.filterText,
                    onValueChange = { onEvent(MoviesScreenUiEvent.OnFilterTextChanged(it)) },
                    label = { Text("Search by Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    singleLine = true
                )

                MovieList(
                    modifier = modifier.padding(bottom = padding.calculateBottomPadding()),
                    movies = movies,
                    isInitialLoading = uiState.initialLoading,
                    navigateToDetailsScreen = navigateToDetailsScreen
                )
            }
        }
    )
}