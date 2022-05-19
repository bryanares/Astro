package com.brian.astro

import android.view.LayoutInflater
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brian.astro.data.Space
import com.brian.astro.databinding.BookmarkItemBinding


class BookMarkAdapter : RecyclerView.Adapter<BookMarkAdapter.BookMarkViewHolder>() {


    private var spaceList = emptyList<Space>()

    class BookMarkViewHolder(val binding: BookmarkItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkViewHolder {
        return BookMarkViewHolder(
            BookmarkItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BookMarkViewHolder, position: Int) {
        holder.binding.apply {
            val currentItem = spaceList[position]
            astroTitle.text = currentItem.title.toString()
            astroDescription.text = currentItem.description.toString()
        }

    }

    override fun getItemCount(): Int {
        return spaceList.size
    }

    fun setData(space: List<Space>){
        this.spaceList = space
        notifyDataSetChanged()

    }

}