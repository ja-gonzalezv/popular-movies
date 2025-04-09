package com.challenge.movies.navigation

import kotlinx.serialization.Serializable

@Serializable
object Movies

@Serializable
data class MovieDetails(val movieId: Long)