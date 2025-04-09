package com.challenge.movies.di

import android.content.Context
import androidx.room.Room
import com.challenge.movies.data.local.MovieDatabase
import com.challenge.movies.data.remote.MovieApi
import com.challenge.movies.data.repository.MovieRepositoryImpl
import com.challenge.movies.domain.repository.MovieRepository
import com.challenge.movies.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer " + Constants.AUTH_TOKEN
                    )
                    .build()
                chain.proceed(newRequest)
            }).build())
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieDatabase: MovieDatabase,
        movieApi: MovieApi
    ): MovieRepository {
        return MovieRepositoryImpl(movieDatabase = movieDatabase, movieApi = movieApi)
    }
}