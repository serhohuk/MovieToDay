package com.sign.movietoday.repository

import com.sign.movietoday.api.RetrofitInstance

class MovieRepository {

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
}