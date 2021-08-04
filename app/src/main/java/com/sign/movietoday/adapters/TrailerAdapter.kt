package com.sign.movietoday.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sign.movietoday.R
import com.sign.movietoday.models.trailerrequest.Result
import kotlinx.android.synthetic.main.trailer_item_layout.view.*

class TrailerAdapter : RecyclerView.Adapter<TrailerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.trailer_item_layout, parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val trailer = differ.currentList[position]
        holder.itemView.apply {
            tv_trailer_name.text = trailer.name
            tv_lang.text = trailer.iso_3166_1
            setOnClickListener {
                onItemClickListener?.let { it(trailer) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem : Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    private var onItemClickListener : ((Result)->Unit)? = null

    fun setOnItemClickListener(listener : (Result)->Unit){
        onItemClickListener = listener
    }


}