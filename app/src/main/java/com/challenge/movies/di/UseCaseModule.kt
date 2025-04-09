package com.challenge.movies.di

import com.challenge.movies.domain.usecase.FilterMoviesUseCase
import com.challenge.movies.domain.usecase.FilterMoviesUseCaseImpl
import com.challenge.movies.domain.usecase.GetMovieDetailsUseCase
import com.challenge.movies.domain.usecase.GetMovieDetailsUseCaseImpl
import com.challenge.movies.domain.usecase.GetMoviesUseCase
import com.challenge.movies.domain.usecase.GetMoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetMoviesUseCase(getMoviesUseCase: GetMoviesUseCaseImpl): GetMoviesUseCase

    @Binds
    abstract fun bindGetMovieDetailsUseCase(getMovieDetailsUseCase: GetMovieDetailsUseCaseImpl): GetMovieDetailsUseCase

    @Binds
    abstract fun bindFilterMoviesUseCase(filterMoviesUseCase: FilterMoviesUseCaseImpl): FilterMoviesUseCase
}