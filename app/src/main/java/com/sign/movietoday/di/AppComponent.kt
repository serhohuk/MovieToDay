package com.sign.movietoday.di

import com.sign.movietoday.ui.MainActivity
import com.sign.movietoday.ui.fragments.MainFragment
import com.sign.movietoday.ui.fragments.MoviesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(moviesListFragment: MoviesListFragment)

    fun inject(mainActivity: MainActivity)

}