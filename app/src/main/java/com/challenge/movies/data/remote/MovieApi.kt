package com.challenge.movies.data.remote

import com.challenge.movies.data.remote.genre.GenreResponseDto
import com.challenge.movies.data.remote.movie.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
    ): MovieResponseDto

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponseDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val MEDIUM_SIZE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        const val LARGE_SIZE_IMAGE_URL = "https://image.tmdb.org/t/p/w780"
    }
}