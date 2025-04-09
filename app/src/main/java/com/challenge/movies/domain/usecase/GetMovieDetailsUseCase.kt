package com.challenge.movies.domain.usecase

import com.challenge.movies.domain.Movie
import com.challenge.movies.domain.repository.MovieRepository
import javax.inject.Inject

fun interface GetMovieDetailsUseCase {
    suspend operator fun invoke(id: Int): Movie
}

class GetMovieDetailsUseCaseImpl @Inject constructor(private val movieRepository: MovieRepository) :
    GetMovieDetailsUseCase {
    override suspend fun invoke(id: Int) = movieRepository.getMovieDetails(id)
}