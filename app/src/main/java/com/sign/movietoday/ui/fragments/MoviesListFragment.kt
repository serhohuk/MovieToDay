package com.sign.movietoday.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.sign.movietoday.R
import com.sign.movietoday.adapters.ListItemDecorator
import com.sign.movietoday.adapters.MovieAdapter
import com.sign.movietoday.adapters.MovieListAdapter
import com.sign.movietoday.application.MyApplication
import com.sign.movietoday.other.Resource
import com.sign.movietoday.ui.MainActivity
import com.sign.movietoday.viewmodels.MovieData
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.movieslist_fragment_layout.*
import javax.inject.Inject
import javax.inject.Named

class MoviesListFragment : Fragment(R.layout.movieslist_fragment_layout) {

    lateinit var viewModel : MovieViewModel
    lateinit var movieListAdapter : MovieListAdapter
    private val args : MoviesListFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appComponent = (requireActivity().application as MyApplication).appComponent
        appComponent.inject(this)
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        checkingMovieDataObserve()

        movieListAdapter.setOnItemClickListener {
            val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun initRecyclerView(){
        movieListAdapter = MovieListAdapter()
        setupRecView()
    }

    private fun setupRecView(){
        val space = resources.getDimensionPixelSize(R.dimen.recycler_view_items_space)
        val itemDecorator = ListItemDecorator(space)
        rc_list_items.apply {
            adapter = movieListAdapter
            layoutManager = GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
            addItemDecoration(itemDecorator)
        }
    }

    private fun checkingMovieDataObserve(){
        tv_movieType.text = args.showingMovieType
        when(args.showingMovieType){
            getString(R.string.top_rated_movies)-> observeMovieData(viewModel.topRatedMovieData)
            getString(R.string.trending_movies_today)-> observeMovieData(viewModel.trendingMovieData)
            getString(R.string.upcoming_movies)->  observeMovieData(viewModel.upcomingMovieData)
        }
    }

    private fun observeMovieData(movieData: MovieData){
        movieData.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Succes->{
                    response.data?.let { trendingResponse ->
                        movieListAdapter.differ.submitList(trendingResponse.results)
                    }
                }
                is Resource.Error -> {
                    response.message?.let {message->
                        Log.e("SIMPLE", "An error occured $message")
                    }
                }
            }  })
    }
}