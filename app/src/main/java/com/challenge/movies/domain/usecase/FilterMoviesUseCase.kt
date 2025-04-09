package com.challenge.movies.domain.usecase

import com.challenge.movies.domain.repository.MovieRepository
import javax.inject.Inject

fun interface FilterMoviesUseCase {
    operator fun invoke(query: String)
}

class FilterMoviesUseCaseImpl @Inject constructor(private val movieRepository: MovieRepository) : FilterMoviesUseCase {
    override fun invoke(query: String) = movieRepository.filterMoviesBy(query)
}