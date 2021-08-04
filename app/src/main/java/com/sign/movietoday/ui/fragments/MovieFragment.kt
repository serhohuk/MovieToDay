package com.sign.movietoday.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sign.movietoday.R
import com.sign.movietoday.adapters.ListItemDecorator
import com.sign.movietoday.adapters.TrailerAdapter
import com.sign.movietoday.other.Constants.IMAGE_LOAD_BASE_URL
import com.sign.movietoday.other.Constants.VIDEO_BASE_URL
import com.sign.movietoday.ui.MainActivity
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.movie_fragment_layout.*
import kotlinx.android.synthetic.main.movie_fragment_layout.view.*

class MovieFragment : Fragment() {

    private val args : MovieFragmentArgs by navArgs()
    private lateinit var viewModel : MovieViewModel
    private lateinit var trailerAdapter : TrailerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.getTrailerByMovieID(args.resultResponse.id, viewModel.requestLang)

        saveItem.setOnClickListener {
            it.visibility = View.INVISIBLE
            deleteItem.visibility = View.VISIBLE
            viewModel.addMovie(args.resultResponse)
            Toast.makeText(activity,"Saved", Toast.LENGTH_SHORT).show()
        }

        deleteItem.setOnClickListener {
            it.visibility = View.INVISIBLE
            saveItem.visibility = View.VISIBLE
            viewModel.deleteMovie(args.resultResponse)
            Toast.makeText(activity,"Unsaved", Toast.LENGTH_SHORT).show()
        }

        btn_watch_trailer.setOnClickListener {
            viewModel.trailerData.observe(viewLifecycleOwner, Observer {
                trailerAdapter.differ.submitList(it.results)
                rec_view_trailers.visibility = View.VISIBLE
            })
        }

        trailerAdapter.setOnItemClickListener {
            val videoLink = VIDEO_BASE_URL+it.key
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoLink)))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_fragment_layout,container,false)
        viewModel = (activity as MainActivity).viewModel
        val imgpath = IMAGE_LOAD_BASE_URL + args.resultResponse.poster_path
        Glide.with(this).load(imgpath).into(view.im_poster)
        view.tv_title.text = args.resultResponse.title
        view.tv_rating.text = args.resultResponse.vote_average.toString()
        view.progressBar_rating.progress = args.resultResponse.vote_average.toInt() * 10
        view.tv_overview.text = args.resultResponse.overview
        view.tv_genre.text = viewModel.handleGenresToString(args.resultResponse.genre_ids)

        checkIfMovieSavedToDB()
        return view
    }

    fun checkIfMovieSavedToDB(){
        viewModel.getMovieByID(args.resultResponse.id).observe(viewLifecycleOwner, Observer {
            if (it!=null){
                saveItem.visibility = View.INVISIBLE
                deleteItem.visibility = View.VISIBLE
            }
        })
    }

    fun setupRecyclerView(){
        val space = resources.getDimensionPixelSize(R.dimen.recycler_view_items_space)
        val itemDecorator = ListItemDecorator(space)
        trailerAdapter = TrailerAdapter()
        rec_view_trailers.apply {
            adapter = trailerAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(itemDecorator)
        }
    }


}