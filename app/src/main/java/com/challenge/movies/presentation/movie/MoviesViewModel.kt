package com.challenge.movies.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.challenge.movies.domain.usecase.FilterMoviesUseCase
import com.challenge.movies.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
    private val filterMoviesUseCase: FilterMoviesUseCase
) : ViewModel() {
    val moviePagingFlow = getMoviesUseCase().cachedIn(viewModelScope)

    private val _state = MutableStateFlow(MoviesScreenUiState())
    val state: StateFlow<MoviesScreenUiState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MoviesScreenUiState()
    )

    private val _sideEffect by lazy { Channel<MoviesScreenUiSideEffect>() }
    val sideEffect: Flow<MoviesScreenUiSideEffect> by lazy { _sideEffect.receiveAsFlow() }

    fun onEvent(event: MoviesScreenUiEvent) {
        when (event) {
            is MoviesScreenUiEvent.OnLoadStateChanged -> {
                when (event.loadState.refresh) {
                    is LoadState.Loading -> {
                        _state.update { it.copy(initialLoading = true) }
                    }

                    is LoadState.NotLoading -> {
                        _state.update { it.copy(initialLoading = false) }
                    }

                    is LoadState.Error -> {
                        _state.update { it.copy(error = (event.loadState.refresh as LoadState.Error).error.message) }
                    }
                }
            }

            is MoviesScreenUiEvent.OnFilterTextChanged -> {
                filterMoviesUseCase(event.filterText.text)
                _state.update { it.copy(filterText = event.filterText) }
            }
        }
    }
}