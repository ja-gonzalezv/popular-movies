package com.challenge.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.challenge.movies.presentation.details.MovieDetailsScreen
import com.challenge.movies.presentation.details.MovieDetailsViewModel
import com.challenge.movies.presentation.movie.MoviesScreen
import com.challenge.movies.presentation.movie.MoviesScreenUiSideEffect
import com.challenge.movies.presentation.movie.MoviesViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Movies) {
        composable<Movies> {
            val viewModel = hiltViewModel<MoviesViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val sideEffects by viewModel.sideEffect.collectAsStateWithLifecycle(initialValue = MoviesScreenUiSideEffect.Idle)
            val movies = viewModel.moviePagingFlow.collectAsLazyPagingItems()

            MoviesScreen(
                uiState = state,
                uiSideEffect = sideEffects,
                movies = movies,
                onEvent = viewModel::onEvent
            ) { movie ->
                navController.navigate(MovieDetails(movieId = movie.id))
            }
        }

        composable<MovieDetails> {
            val viewModel = hiltViewModel<MovieDetailsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            MovieDetailsScreen(uiState = state, onBack = { navController.popBackStack() })
        }
    }
}