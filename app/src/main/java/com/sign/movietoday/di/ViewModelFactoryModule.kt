package com.sign.movietoday.di

import androidx.lifecycle.ViewModelProvider
import com.sign.movietoday.viewmodels.MovieViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Named


@Module
abstract class ViewModelFactoryModule {

    @Binds
    @Named("viewModel")
    abstract fun bindsMovieViewModelFactory(factory : MovieViewModelFactory) : ViewModelProvider.Factory

}