package com.sign.movietoday.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.movietoday.models.genresrequest.Genre
import com.sign.movietoday.models.genresrequest.GenreResponse
import com.sign.movietoday.models.movielistrequest.MovieResponse
import com.sign.movietoday.models.movielistrequest.Result
import com.sign.movietoday.models.trailerrequest.TrailerResponse
import com.sign.movietoday.other.Resource
import com.sign.movietoday.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

typealias MovieData = MutableLiveData<Resource<MovieResponse>>

class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    var  isFirstStart = true
    val readAllDataFromDB : LiveData<List<Result>>

    var searchMoviesPage = 1
    var topRatedhMoviesPage = 1
    var byGenresMoviesPage = 1
    var trendingMoviesPage = 1
    var upcomingMoviesPage = 1

    var searchMovieData : MovieData = MutableLiveData()
    var topRatedMovieData : MovieData = MutableLiveData()
    var byGenresMovieData : MovieData = MutableLiveData()
    var trendingMovieData : MovieData = MutableLiveData()
    var upcomingMovieData : MovieData = MutableLiveData()
    var trailerData : MutableLiveData<TrailerResponse> = MutableLiveData()



    var genresData : List<Genre> ? = null

    init {
        readAllDataFromDB = repository.readAllData
    }

    fun searchMovie(searchQuery : String, language : String){
        viewModelScope.launch(Dispatchers.IO){
            val response = repository.searchMovie(searchQuery, language, searchMoviesPage)
            safeMovieRequest(searchMovieData,response)
        }
    }

    fun topRatedMovies(language: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTopRatedMovies(language, topRatedhMoviesPage)
            safeMovieRequest(topRatedMovieData,response)
        }
    }

    fun moviesByGenres(genres : String, language: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMoviesByGenre(genres, language , byGenresMoviesPage )
            safeMovieRequest(byGenresMovieData, response)
        }
    }

    fun trendingMoviesToday(language: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTrendingMoviesToday(language, trendingMoviesPage)
            safeMovieRequest(trendingMovieData, response)
        }
    }

    fun upcomingMovies(language: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUpcomingMovies(language, upcomingMoviesPage)
            safeMovieRequest(upcomingMovieData, response)
        }
    }

    fun getAllGenres(language: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllGenres(language)
            try {
                if (response.isSuccessful){
                    response.body()?.let { responseBody-> genresData = responseBody.genres }
                }
                Log.e("Error","${response.message()}")
            }
            catch (t : Throwable){
                Log.e("Error","Genres dont have response")
            }

        }
    }

    fun handleGenresToString(list: List<Int>) : String {
        val resultListGenres = mutableListOf<String>()
        val responseList = genresData!!
        for(genresID in list){
            for (genreObject in responseList){
                if (genresID==genreObject.id){
                    resultListGenres.add(genreObject.name)
                    break
                }
            }
        }
        return listToString(resultListGenres)
    }

    fun listToString(list:List<String>) : String{
        val strList = list.toString()
        return strList.substring(1,strList.length-1)
    }

    private fun safeMovieRequest(movieData : MovieData,response : Response<MovieResponse>) {
        movieData.postValue(Resource.Loading())
        try {
            movieData.postValue(handleMoviesResponse(response))
        }
        catch (t : Throwable){
            searchMovieData.postValue(Resource.Error("Something went wrong"))
        }
    }

    private fun handleMoviesResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful){
            response.body()?.let { responseBody ->
                return Resource.Succes(responseBody)
            }
        }
        return Resource.Error(response.message())
    }

    fun getTrailerByMovieID(movieID : Int, language: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTrailerByMovieID(movieID, language)
            try {
                if (response.isSuccessful){
                    response.body()?.let { responseBody-> trailerData.postValue(responseBody) }
                }
                Log.e("Error","${response.message()}")
            }
            catch (t : Throwable){
                Log.e("Error","Trailer doesn`t have response")
            }
        }
    }

    fun addMovie(movie : Result){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movie)
        }
    }

    fun deleteMovie(movie : Result){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movie)
        }
    }

    fun getMovieByID(id : Int) : LiveData<Result>{
        return repository.getByID(id)
    }

}