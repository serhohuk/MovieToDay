package com.sign.movietoday.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sign.movietoday.R
import com.sign.movietoday.other.Constants.IMAGE_LOAD_BASE_URL
import com.sign.movietoday.ui.MainActivity
import com.sign.movietoday.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.movie_fragment_layout.view.*

class MovieFragment : Fragment() {

    private val args : MovieFragmentArgs by navArgs()
    private lateinit var viewModel : MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        return view
    }
}