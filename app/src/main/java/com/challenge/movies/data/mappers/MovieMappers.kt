package com.challenge.movies.data.mappers

import com.challenge.movies.data.local.movie.MovieEntity
import com.challenge.movies.data.remote.MovieApi
import com.challenge.movies.data.remote.movie.MovieDto
import com.challenge.movies.domain.Movie

fun MovieDto.toMovieEntity(genres: List<String>): MovieEntity {
    return MovieEntity(
        id = id,
        name = title,
        posterUrl = posterPath?.let { MovieApi.MEDIUM_SIZE_IMAGE_URL + it },
        backdropUrl = backdropPath?.let { MovieApi.LARGE_SIZE_IMAGE_URL + it },
        genres = genres,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        name = name,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        genres = genres,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}