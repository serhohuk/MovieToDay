package com.sign.movietoday.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sign.movietoday.models.movielistrequest.Result

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(result: Result)

    @Query("SELECT * FROM saved_movies")
    fun readAllData() : LiveData<List<Result>>

    @Delete
    suspend fun deleteMovie(result: Result)

    @Query("SELECT * FROM saved_movies WHERE id=:id")
    fun getByID(id : Int) : LiveData<Result>
}