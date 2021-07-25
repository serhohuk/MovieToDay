package com.sign.movietoday.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sign.movietoday.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieViewModelFactory @Inject constructor(val repository: MovieRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repository) as T
    }
}