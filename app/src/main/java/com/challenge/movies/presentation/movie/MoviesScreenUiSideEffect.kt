package com.challenge.movies.presentation.movie

sealed class MoviesScreenUiSideEffect {
    data object Idle : MoviesScreenUiSideEffect()
    data class ShowError(val message: String) : MoviesScreenUiSideEffect()
}