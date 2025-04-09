package com.challenge.movies.presentation.movie

import androidx.compose.ui.text.input.TextFieldValue

data class MoviesScreenUiState(
    val initialLoading: Boolean = true,
    val error: String? = null,
    val filterText: TextFieldValue = TextFieldValue(""),
)