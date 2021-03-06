package com.sign.movietoday.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sign.movietoday.R
import com.sign.movietoday.models.movielistrequest.Result
import com.sign.movietoday.other.Constants
import kotlinx.android.synthetic.main.movie_item_list.view.im_view_poster
import kotlinx.android.synthetic.main.movie_item_list.view.tv_rating
import kotlinx.android.synthetic.main.movie_item_list.view.tv_title
import kotlinx.android.synthetic.main.movie_item_list.view.*

class MovieListAdapter: PagingDataAdapter<Result, MovieListAdapter.MoviesViewHolder>(DiffUtilCallBack()){

    inner class MoviesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(result: Result){
            val imgURL = Constants.IMAGE_LOAD_BASE_URL +result.poster_path
            Glide.with(itemView.im_view_poster).load(imgURL).into(itemView.im_view_poster)
            itemView.tv_title.text = result.title
            itemView.tv_rating.text = result.vote_average.toString()
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_list, parent,false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(getItem(position)!!) }
            }
        }
    }

    private var onItemClickListener : ((Result)->Unit)? = null

    fun setOnItemClickListener(listener : (Result)->Unit){
        onItemClickListener = listener
    }
}
