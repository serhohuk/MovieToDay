package com.sign.movietoday.di

import com.sign.movietoday.ui.fragments.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)

}