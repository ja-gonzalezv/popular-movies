package com.challenge.movies.data.local.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieRemoteKeysEntity(
    @PrimaryKey
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?
)