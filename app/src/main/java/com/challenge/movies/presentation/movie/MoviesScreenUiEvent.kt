package com.challenge.movies.presentation.movie

import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.CombinedLoadStates

sealed class MoviesScreenUiEvent {
    data class OnLoadStateChanged(val loadState: CombinedLoadStates): MoviesScreenUiEvent()
    data class OnFilterTextChanged(val filterText: TextFieldValue): MoviesScreenUiEvent()
}