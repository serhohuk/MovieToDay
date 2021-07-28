package com.sign.movietoday.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sign.movietoday.R
import com.sign.movietoday.adapters.HorizontalItemDecorator
import com.sign.movietoday.adapters.MovieAdapter
import com.sign.movietoday.application.MyApplication
import com.sign.movietoday.other.Constants.LANG_UA
import com.sign.movietoday.other.Resource
import com.sign.movietoday.ui.MainActivity
import com.sign.movietoday.viewmodels.MovieData
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.main_fragment_layout.*


class MainFragment : Fragment(R.layout.main_fragment_layout) {


    lateinit var movieAdapterTopRated: MovieAdapter
    lateinit var movieAdapterTrending : MovieAdapter
    lateinit var movieAdapterUpcoming : MovieAdapter

    lateinit var viewModel : MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appComponent = (requireActivity().application as MyApplication).appComponent
        appComponent.inject(this)
        viewModel = (activity as MainActivity).viewModel

        initRecViews()
        viewModel.trendingMoviesToday(LANG_UA)
        viewModel.upcomingMovies(LANG_UA)
        viewModel.topRatedMovies(LANG_UA)

        Toast.makeText(requireContext(),"ONVIEWCREATED",Toast.LENGTH_SHORT).show()

        observeMovieData(viewModel.topRatedMovieData,movieAdapterTopRated)
        observeMovieData(viewModel.upcomingMovieData,movieAdapterUpcoming)
        observeMovieData(viewModel.trendingMovieData, movieAdapterTrending)

        button_top_rated.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_moviesListFragment)
        }

        button_trending.setOnClickListener {

        }
        button_upcoming.setOnClickListener {  }
    }



    private fun setupRecyclerView(recyclerView: RecyclerView,myMovieAdapter : MovieAdapter){
        val space = resources.getDimensionPixelSize(R.dimen.recycler_view_items_space)
        val itemDecorator = HorizontalItemDecorator(space)
        recyclerView.apply {
            adapter = myMovieAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(itemDecorator)
        }
    }

    private fun observeMovieData(vmMovieData: MovieData, movieAdapter: MovieAdapter){
        vmMovieData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Succes->{
                    response.data?.let { trendingResponse ->
                        movieAdapter.differ.submitList(trendingResponse.results)
                    }
                }
                is Resource.Error -> {
                    response.message?.let {message->
                        Log.e("SIMPLE", "An error occured $message")
                    }
                }
            }
        })
    }

    private fun initRecViews(){
        movieAdapterTopRated = MovieAdapter()
        movieAdapterUpcoming = MovieAdapter()
        movieAdapterTrending = MovieAdapter()
        setupRecyclerView(rv_top_rated,movieAdapterTopRated)
        setupRecyclerView(rv_trending,movieAdapterTrending)
        setupRecyclerView(rv_upcoming,movieAdapterUpcoming)
    }


}