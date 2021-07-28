package com.sign.movietoday.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sign.movietoday.R
import com.sign.movietoday.adapters.ListItemDecorator
import com.sign.movietoday.adapters.MovieAdapter
import com.sign.movietoday.application.MyApplication
import com.sign.movietoday.other.Resource
import com.sign.movietoday.ui.MainActivity
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.movieslist_fragment_layout.*
import javax.inject.Inject
import javax.inject.Named

class MoviesListFragment : Fragment(R.layout.movieslist_fragment_layout) {

    lateinit var viewModel : MovieViewModel
    lateinit var movieListAdapter : MovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appComponent = (requireActivity().application as MyApplication).appComponent
        appComponent.inject(this)
        viewModel = (activity as MainActivity).viewModel

        initRecyclerView()
        Log.e("SIMPLE", "${viewModel.topRatedMovieData.value}")
        viewModel.topRatedMovieData.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Succes->{
                    response.data?.let { trendingResponse ->
                        Log.e("SIMPLE", "${trendingResponse.results}")
                        Log.e("SIMPLE", "${viewModel.topRatedMovieData.value}")

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

    private fun initRecyclerView(){
        movieListAdapter = MovieAdapter()
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


}