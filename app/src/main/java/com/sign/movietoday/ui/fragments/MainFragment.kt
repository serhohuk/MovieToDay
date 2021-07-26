package com.sign.movietoday.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sign.movietoday.R
import com.sign.movietoday.adapters.HorizontalItemDecorator
import com.sign.movietoday.adapters.MovieAdapter
import com.sign.movietoday.application.MyApplication
import com.sign.movietoday.other.Constants.LANG_UA
import com.sign.movietoday.other.Resource
import com.sign.movietoday.viewmodels.MovieData
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.main_fragment_layout.*
import kotlinx.android.synthetic.main.main_fragment_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class MainFragment : Fragment(R.layout.main_fragment_layout) {

    @Inject
    @Named("viewModel")
    lateinit var movieViewModelFactory :ViewModelProvider.Factory
    lateinit var movieAdapter: MovieAdapter
    lateinit var movieAdapter2: MovieAdapter

    private val viewModel : MovieViewModel by viewModels { movieViewModelFactory  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appComponent = (requireActivity().application as MyApplication).appComponent
        appComponent.inject(this)
        viewModel.trendingMoviesToday(LANG_UA)
        viewModel.topRatedMovies(LANG_UA)
        movieAdapter = MovieAdapter()
        movieAdapter2 = MovieAdapter()
        Toast.makeText(requireContext(),"ONVIEWCREATED",Toast.LENGTH_SHORT).show()
        setupRecyclerView(rv_trending,movieAdapter)
        setupRecyclerView(rv_top_rated,movieAdapter2)
        observeMovieData(viewModel.trendingMovieData)
        //observeMovieData(viewModel.topRatedMovieData)
        viewModel.topRatedMovieData.observe(viewLifecycleOwner, Observer { response ->
                when(response){
                    is Resource.Succes->{
                        response.data?.let { trendingResponse ->
                            movieAdapter2.differ.submitList(trendingResponse.results)
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



    private fun setupRecyclerView(recyclerView: RecyclerView,myMovieAdapter : MovieAdapter){
        val space = resources.getDimensionPixelSize(R.dimen.recycler_view_items_space)
        val itemDecorator = HorizontalItemDecorator(space)
        recyclerView.apply {
            adapter = myMovieAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(itemDecorator)
        }
    }

    private fun observeMovieData(vmMovieData: MovieData){
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



}