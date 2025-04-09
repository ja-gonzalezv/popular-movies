package com.challenge.movies.data.local.genre

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface GenreDao {
    @Upsert
    suspend fun upsertAll(genres: List<GenreEntity>)

    @Query("SELECT * FROM genreentity")
    suspend fun getAll(): List<GenreEntity>

    @Query("DELETE FROM genreentity")
    suspend fun clearAll()
}