package com.challenge.movies.data.local.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movieentity WHERE name LIKE :filter")
    fun getItemsFiltered(filter: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movieentity WHERE movieId = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity

    @Query("DELETE FROM movieentity")
    suspend fun clearAll()
}