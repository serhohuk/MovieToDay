package com.sign.movietoday.di

import androidx.lifecycle.ViewModelProvider
import com.sign.movietoday.viewmodels.MovieViewModelFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindsMovieViewModelFactory(factory : MovieViewModelFactory) : ViewModelProvider.Factory

}