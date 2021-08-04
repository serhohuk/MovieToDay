package com.sign.movietoday.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sign.movietoday.room.AppDao
import com.sign.movietoday.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val application : Application) {

    @Singleton
    @Provides
    fun provideDatabase(context: Context)=
        Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "my_db")
        .build()


    @Singleton
    @Provides
    fun provideApplication() : Application = application

    @Singleton
    @Provides
    fun provideContext() : Context = application

    @Singleton
    @Provides
    fun provideDao(db : AppDatabase) : AppDao = db.getMovieDao()

}