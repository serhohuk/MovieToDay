package com.sign.movietoday.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sign.movietoday.R
import com.sign.movietoday.adapters.ListItemDecorator
import com.sign.movietoday.adapters.MovieListAdapter
import com.sign.movietoday.other.Constants.LANG_ENG
import com.sign.movietoday.other.Constants.LANG_RU
import com.sign.movietoday.other.Constants.LANG_UA
import com.sign.movietoday.other.Resource
import com.sign.movietoday.ui.MainActivity
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.movieslist_fragment_layout.*
import kotlinx.android.synthetic.main.search_fragment_layout.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.search_fragment_layout) {
    private lateinit var viewModel: MovieViewModel
    private var currentSearchLanguage = LANG_ENG
    private lateinit var searchAdapter : MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecView()

        spinner_lang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, item_id: Long) {
                when(adapterView?.getItemAtPosition(position).toString()){
                    resources.getStringArray(R.array.lang_name)[0]-> currentSearchLanguage = LANG_ENG
                    resources.getStringArray(R.array.lang_name)[1]-> currentSearchLanguage = LANG_UA
                    resources.getStringArray(R.array.lang_name)[2]-> currentSearchLanguage = LANG_RU
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        var job : Job? = null
        et_search.addTextChangedListener { editable->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchMovie(editable.toString(),currentSearchLanguage)
                    }
                }
            }
        }

        viewModel.searchMovieData.observe(viewLifecycleOwner, {response ->
            when(response){
                is Resource.Succes->{
                    response.data?.let { searchResponse ->
                        searchAdapter.differ.submitList(searchResponse.results)
                    }
                }
                is Resource.Error -> {
                    response.message?.let {message->
                        Log.e("SIMPLE", "An error occured $message")
                    }
                }
            }  })

        searchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToMovieFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setupRecView(){
        searchAdapter = MovieListAdapter()
        val space = resources.getDimensionPixelSize(R.dimen.recycler_view_items_space)
        val itemDecorator = ListItemDecorator(space)
        rv_search.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(activity,2, GridLayoutManager.VERTICAL,false)
            addItemDecoration(itemDecorator)
        }
    }
}