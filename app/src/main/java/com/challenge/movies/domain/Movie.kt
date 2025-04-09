package com.challenge.movies.domain

import androidx.compose.runtime.Stable

@Stable
data class Movie(
    val id: Long,
    val name: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val genres: List<String>,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double
)