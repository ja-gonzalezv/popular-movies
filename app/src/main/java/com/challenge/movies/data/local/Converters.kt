package com.challenge.movies.data.local

import androidx.room.TypeConverter
import com.challenge.movies.data.local.genre.GenreEntity

class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.split(",")
    }

    @TypeConverter
    fun fromGenreList(value: List<GenreEntity>?): String? {
        return value?.joinToString(";") { "${it.id},${it.name}" }
    }

    @TypeConverter
    fun toGenreList(value: String?): List<GenreEntity>? {
        return value?.let {
            it.split(";").mapNotNull { address ->
                val parts = address.split(",")
                if (parts.size == 2) {
                    GenreEntity(parts[0].toLong(), parts[1])
                } else {
                    null
                }
            }
        }
    }
}