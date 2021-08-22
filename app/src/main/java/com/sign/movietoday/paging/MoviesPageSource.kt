package com.sign.movietoday.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sign.movietoday.api.MovieAPI
import com.sign.movietoday.models.movielistrequest.MovieResponse
import com.sign.movietoday.models.movielistrequest.Result
import com.sign.movietoday.other.RequestType
import com.sign.movietoday.repository.MovieRepository
import retrofit2.Response


class MoviesPageSource(
    private val repository: MovieRepository, private val language : String, private val requestType: RequestType
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state?.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1

        return try {
            val data =  getData(page)
            LoadResult.Page(
                data = data.body()?.results!!,
                prevKey = if (page==1) null else page-1,
                nextKey = if (data.body()?.results?.isEmpty()!!) null else page+1
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }

    private suspend fun getData(page : Int): Response<MovieResponse> {
        when(requestType){
            RequestType.TopRated -> return repository.getTopRatedMovies(page = page,language = language)
            RequestType.Trending -> return repository.getTrendingMoviesToday(page= page, language = language)
            RequestType.Upcoming -> return repository.getUpcomingMovies(page = page, language = language)
        }
    }

}