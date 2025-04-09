package com.challenge.movies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.challenge.movies.data.local.MovieDatabase
import com.challenge.movies.data.mappers.toMovie
import com.challenge.movies.data.remote.MovieApi
import com.challenge.movies.data.remote.MovieRemoteMediator
import com.challenge.movies.domain.Movie
import com.challenge.movies.domain.repository.MovieRepository
import com.challenge.movies.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDatabase: MovieDatabase,
    private val movieApi: MovieApi
) : MovieRepository {

    private val _filterQuery = MutableStateFlow("")

    override fun filterMoviesBy(query: String) {
        _filterQuery.update { query }
    }

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    override fun getMovies(): Flow<PagingData<Movie>> {
        return _filterQuery.flatMapLatest { filter ->
            Pager(
                config = PagingConfig(
                    pageSize = Constants.ITEMS_PER_PAGE,
                    prefetchDistance = Constants.ITEMS_PER_PAGE * 3
                ),
                remoteMediator = MovieRemoteMediator(
                    movieDatabase = movieDatabase,
                    movieApi = movieApi
                ),
                pagingSourceFactory = {
                    movieDatabase.movieDao.getItemsFiltered("%$filter%")
                }
            ).flow.map { pagingData -> pagingData.map { it.toMovie() } }
        }
    }

    override suspend fun getMovieDetails(movieId: Int) = movieDatabase.movieDao.getMovieById(movieId = movieId).toMovie()
}