package com.challenge.movies.data.remote.genre

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(val id: Long, val name: String)