package com.challenge.movies.domain.repository

import androidx.paging.PagingData
import com.challenge.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun filterMoviesBy(query: String)
    fun getMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(movieId: Int): Movie
}