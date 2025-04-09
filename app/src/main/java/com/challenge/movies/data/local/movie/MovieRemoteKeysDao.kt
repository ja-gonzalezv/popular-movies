package com.challenge.movies.data.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieRemoteKeysDao {

    @Query("SELECT * FROM movieremotekeysentity WHERE id = :id")
    suspend fun getRemoteKeys(id: Long): MovieRemoteKeysEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<MovieRemoteKeysEntity>)

    @Query("DELETE FROM movieremotekeysentity")
    suspend fun clearAll()
}
