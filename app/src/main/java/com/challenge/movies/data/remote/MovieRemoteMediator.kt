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

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    // Start from the first page
                    1
                }

                LoadType.PREPEND -> {
                    // We don't need to load more pages when prepending
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    state.anchorPosition?.let { anchorPosition ->
                        (anchorPosition / state.config.pageSize) + 1
                    } ?: 1
                }
            }

            val moviesResponse = movieApi.getMovies(page = currentPage)
            val genreEntities = getGenres() ?: movieDatabase.genreDao.getAll()

            // Usage of withTransaction is beneficial in this case, since we only want to execute all transactions if all of them succeed
            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.movieDao.clearAll()
                }
                movieDatabase.genreDao.clearAll()

                val movieEntities =
                    moviesResponse.results.map { it.toMovieEntity(genreEntities.filterGenresBy(it)) }

                movieDatabase.movieDao.upsertAll(movieEntities)
                movieDatabase.genreDao.upsertAll(genreEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = moviesResponse.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
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