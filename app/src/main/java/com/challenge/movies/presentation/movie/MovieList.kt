package com.challenge.movies.presentation.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.challenge.movies.domain.Movie

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    isInitialLoading: Boolean,
    navigateToDetailsScreen: (Movie) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (isInitialLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = movies.itemCount,
                    key = movies.itemKey { it.id }
                ) { index ->
                    val movie = movies[index]
                    if (movie != null) {
                        MovieItem(
                            modifier = Modifier.fillMaxWidth(),
                            movie = movie,
                            onClick = {
                                navigateToDetailsScreen(movie)
                            }
                        )
                    }
                }
            }
        }
    }
}