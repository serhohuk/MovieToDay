package com.sign.movietoday.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.sign.movietoday.R
import com.sign.movietoday.application.MyApplication
import com.sign.movietoday.other.Constants.LANG_UA
import com.sign.movietoday.other.Resource
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.main_fragment_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class MainFragment : Fragment(R.layout.main_fragment_layout) {

    @Inject
    @Named("viewModel")
    lateinit var movieViewModelFactory :ViewModelProvider.Factory

    private val viewModel : MovieViewModel by viewModels { movieViewModelFactory  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appComponent = (requireActivity().application as MyApplication).appComponent
        appComponent.inject(this)
        viewModel.trendingMoviesToday(LANG_UA)
        viewModel.trendingMovieData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Succes->{
                    response.data?.let { trendingResponse ->
                        val oneItem = trendingResponse.results.get(0)
                        view.tv_title.text = oneItem.title
                        view.tv_overview.text = oneItem.overview
                        view.tv_rating.text = oneItem.vote_average.toString()
                    }
                }
                is Resource.Error -> {
                    response.message?.let {message->
                        Toast.makeText(requireContext(),"PIZDEC",Toast.LENGTH_SHORT).show()
                        Log.e("SIMPLE", "An error occured $message")

                    }
                }
            }

        })

    }
}