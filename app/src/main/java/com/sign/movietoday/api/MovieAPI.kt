package com.sign.movietoday.api

import com.sign.movietoday.models.genresrequest.GenreResponse
import com.sign.movietoday.models.movielistrequest.MovieResponse
import com.sign.movietoday.models.trailerrequest.TrailerResponse
import com.sign.movietoday.other.Constants.API_KEY
import com.sign.movietoday.other.Constants.LANG_ENG
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    //+
    @GET("3/search/movie")
    suspend fun searchMovie(
        @Query("api_key")
        apiKey : String = API_KEY,
        @Query("query")
        query : String,
        @Query("language")
        language : String = LANG_ENG,
        @Query("page")
        pageNumber : Int = 1,
        @Query("include_adult")
        withAdult : Boolean = false
    ) : Response<MovieResponse>
    //+
    @GET("3/discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("with_genres")
        genres : String,
        @Query("language")
        language : String = LANG_ENG,
        @Query("page")
        pageNumber : Int = 1
    ) : Response<MovieResponse>
    //+
    @GET("3/genre/movie/list")
    suspend fun getAllGenres(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = LANG_ENG
    ) : Response<GenreResponse>
    //+
    @GET("3/trending/movie/day")
    suspend fun getTrendingMoviesToday(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = LANG_ENG,
        @Query("page")
        pageNumber: Int = 1
    ) : Response<MovieResponse>
    //+
    @GET("3/movie/{movie_id}/videos")
    suspend fun getTrailerByMovieID(
        @Path(value ="movie_id" ,encoded = true)
        movieID : Int,
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = LANG_ENG
    ) : Response<TrailerResponse>
    //+
    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = LANG_ENG,
        @Query("page")
        pageNumber: Int = 1
    ) : Response<MovieResponse>
    //+
    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = LANG_ENG,
        @Query("page")
        pageNumber: Int = 1
    ) : Response<MovieResponse>
}