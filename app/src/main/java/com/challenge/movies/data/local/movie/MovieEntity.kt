package com.challenge.movies.data.local.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val genres: List<String>,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double
)