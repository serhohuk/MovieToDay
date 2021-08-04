package com.sign.movietoday.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sign.movietoday.models.movielistrequest.Result
import com.sign.movietoday.other.Converter

@Database(entities = [Result::class],version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getMovieDao() : AppDao
}