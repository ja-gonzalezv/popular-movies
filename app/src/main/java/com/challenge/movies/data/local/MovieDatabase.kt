package com.challenge.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.challenge.movies.data.local.genre.GenreDao
import com.challenge.movies.data.local.genre.GenreEntity
import com.challenge.movies.data.local.movie.MovieDao
import com.challenge.movies.data.local.movie.MovieEntity

@Database(
    entities = [MovieEntity::class, GenreEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao
    abstract val genreDao: GenreDao
}