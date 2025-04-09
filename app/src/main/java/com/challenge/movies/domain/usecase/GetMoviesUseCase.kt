package com.challenge.movies.domain.usecase

import androidx.paging.PagingData
import com.challenge.movies.domain.Movie
import com.challenge.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetMoviesUseCaseImpl @Inject constructor(private val movieRepository: MovieRepository) : GetMoviesUseCase {
    override fun invoke() = movieRepository.getMovies()
}