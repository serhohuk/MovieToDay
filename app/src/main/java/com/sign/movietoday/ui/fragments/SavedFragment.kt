package com.sign.movietoday.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sign.movietoday.R
import com.sign.movietoday.adapters.ListItemDecorator
import com.sign.movietoday.adapters.MovieListAdapter
import com.sign.movietoday.ui.MainActivity
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.movieslist_fragment_layout.*
import kotlinx.android.synthetic.main.saved_fragment_layout.*

class SavedFragment : Fragment(R.layout.saved_fragment_layout) {

    private lateinit var viewModel : MovieViewModel
    private lateinit var savedAdapter: MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initAdapter()
        viewModel.readAllDataFromDB.observe(viewLifecycleOwner, Observer {
            savedAdapter.differ.submitList(it)
        })

        savedAdapter.setOnItemClickListener {
            val action = SavedFragmentDirections.actionSavedFragmentToMovieFragment(it)
            findNavController().navigate(action)
        }
    }

    fun initAdapter(){
        savedAdapter = MovieListAdapter()
        val space = resources.getDimensionPixelSize(R.dimen.recycler_view_items_space)
        val itemDecorator = ListItemDecorator(space)
        rv_saved.apply {
            adapter = savedAdapter
            layoutManager = GridLayoutManager(activity,2, GridLayoutManager.VERTICAL,false)
            addItemDecoration(itemDecorator)
        }
    }
}