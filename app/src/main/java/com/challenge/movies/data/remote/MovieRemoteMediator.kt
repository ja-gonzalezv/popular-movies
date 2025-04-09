package com.challenge.movies.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.challenge.movies.data.local.genre.GenreEntity
import com.challenge.movies.data.local.MovieDatabase
import com.challenge.movies.data.local.movie.MovieEntity
import com.challenge.movies.data.local.movie.MovieRemoteKeysEntity
import com.challenge.movies.data.mappers.toGenreEntity
import com.challenge.movies.data.mappers.toMovieEntity
import com.challenge.movies.data.remote.movie.MovieDto
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieDatabase: MovieDatabase,
    private val movieApi: MovieApi,
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao = movieDatabase.movieDao
    private val movieRemoteKeysDao = movieDatabase.movieRemoteKeysDao
    private val genreDao = movieDatabase.genreDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    state.getRemoteKeyClosestToCurrentPosition()?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = state.getRemoteKeyForFirstItem()
                    remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = state.getRemoteKeyForLastItem()
                    remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            val moviesResponse = movieApi.getMovies(page = currentPage)
            val genreEntities = getGenres() ?: genreDao.getAll()

            val endOfPaginationReached = moviesResponse.results.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            // Usage of withTransaction is beneficial in this case, since we only want to execute all transactions if all of them succeed
            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearAll()
                    movieRemoteKeysDao.clearAll()
                }
                genreDao.clearAll()

                val (movieEntities, movieRemoteKeysEntities) =
                    moviesResponse.results.map {
                        it.toMovieEntity(genreEntities.filterGenresBy(it)) to MovieRemoteKeysEntity(
                            id = it.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }.unzip()

                movieDao.upsertAll(movieEntities)
                movieRemoteKeysDao.insertAll(movieRemoteKeysEntities)
                genreDao.upsertAll(genreEntities)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun PagingState<Int, MovieEntity>.getRemoteKeyClosestToCurrentPosition(): MovieRemoteKeysEntity? {
        return anchorPosition?.let { position ->
            closestItemToPosition(position)?.movieId?.let { id ->
                movieRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun PagingState<Int, MovieEntity>.getRemoteKeyForFirstItem(): MovieRemoteKeysEntity? {
        return firstItemOrNull()?.movieId?.let { id -> movieRemoteKeysDao.getRemoteKeys(id = id) }
    }

    private suspend fun PagingState<Int, MovieEntity>.getRemoteKeyForLastItem(): MovieRemoteKeysEntity? {
        return lastItemOrNull()?.movieId?.let { id -> movieRemoteKeysDao.getRemoteKeys(id = id) }
    }

    private suspend fun getGenres(): List<GenreEntity>? {
        try {
            val genres = movieApi.getGenres()
            return genres.genres.map { it.toGenreEntity() }
        } catch (e: IOException) {
            Log.e("", "MovieRemoteMediator", e)
        } catch (e: HttpException) {
            Log.e("", "MovieRemoteMediator", e)
        }
        return null
    }

    private fun List<GenreEntity>.filterGenresBy(movie: MovieDto): List<String> {
        val genreMap = associateBy { it.id }.mapValues { it.value.name }
        return movie.genreIds.mapNotNull { genreMap[it] }
    }
}