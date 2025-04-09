package com.challenge.movies.data.local.movie

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["movieId"], unique = true)] // Apply unique constraint to non-primary column
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movieId: Long,
    val name: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val genres: List<String>,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double
)