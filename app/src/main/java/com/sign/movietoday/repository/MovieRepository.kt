package com.sign.movietoday.repository

import androidx.lifecycle.LiveData
import com.sign.movietoday.api.RetrofitInstance
import com.sign.movietoday.models.movielistrequest.Result
import com.sign.movietoday.room.AppDao
import javax.inject.Inject

class MovieRepository @Inject constructor(private val dao: AppDao) {

    suspend fun searchMovie(searchQuery : String, language : String, page : Int) =
        RetrofitInstance.api.searchMovie(query = searchQuery, language = language, pageNumber = page)

    suspend fun getMoviesByGenre(genres : String, language: String, page: Int) =
        RetrofitInstance.api.getMoviesByGenre(genres = genres, language = language, pageNumber = page)

    suspend fun getTrendingMoviesToday(language: String, page: Int) =
        RetrofitInstance.api.getTrendingMoviesToday(language = language, pageNumber = page)

    suspend fun getUpcomingMovies(language: String, page: Int) =
        RetrofitInstance.api.getUpcomingMovies(language = language, pageNumber = page)

    suspend fun getTopRatedMovies(language: String, page: Int) =
        RetrofitInstance.api.getTopRatedMovies(language = language, pageNumber = page)

    suspend fun getAllGenres(language: String) =
        RetrofitInstance.api.getAllGenres(language = language)

    suspend fun getTrailerByMovieID(movieID : Int, language: String) =
        RetrofitInstance.api.getTrailerByMovieID(movieID = movieID, language = language)

    suspend fun insertMovie(result: Result){
        dao.insertMovie(result)
    }

    suspend fun deleteMovie(result: Result){
        dao.deleteMovie(result)
    }

    val readAllData : LiveData<List<Result>> = dao.readAllData()

    fun getByID(id : Int) : LiveData<Result> = dao.getByID(id)
}