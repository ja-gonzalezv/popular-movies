package com.challenge.movies.data.remote.genre

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponseDto(val genres: List<GenreDto>)