package com.challenge.movies.data.mappers

import com.challenge.movies.data.local.genre.GenreEntity
import com.challenge.movies.data.remote.genre.GenreDto

fun GenreDto.toGenreEntity(): GenreEntity {
    return GenreEntity(id = id, name = name)
}