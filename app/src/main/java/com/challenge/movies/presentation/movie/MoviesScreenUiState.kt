package com.challenge.movies.presentation.movie

import androidx.compose.ui.text.input.TextFieldValue

data class MoviesScreenUiState(
    val initialLoading: Boolean = true,
    val filterText: TextFieldValue = TextFieldValue(""),
)