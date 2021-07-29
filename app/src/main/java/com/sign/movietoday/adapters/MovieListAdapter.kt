package com.sign.movietoday.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sign.movietoday.R
import com.sign.movietoday.models.movielistrequest.Result
import com.sign.movietoday.other.Constants
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.MoviesViewHolder>(){

    inner class MoviesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_list, parent,false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.itemView.apply {
            val imgURL = Constants.IMAGE_LOAD_BASE_URL +movie.poster_path
            Glide.with(this).load(imgURL).into(im_view_poster)
            tv_title.text = movie.title
            tv_rating.text = movie.vote_average.toString()
            setOnClickListener {
                onItemClickListener?.let { it(movie) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((Result)->Unit)? = null

    fun setOnItemClickListener(listener : (Result)->Unit){
        onItemClickListener = listener
    }
}
