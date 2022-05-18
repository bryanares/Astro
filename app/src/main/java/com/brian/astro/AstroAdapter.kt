package com.brian.astro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.brian.astro.databinding.AstroItemBinding
import com.squareup.picasso.Picasso

class AstroAdapter(astroList:MutableList<Astro>, private val listener: HomeFragment) :
    RecyclerView.Adapter<AstroAdapter.AstroViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }


    inner class AstroViewHolder(val binding: AstroItemBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(binding.root){

            init {
                binding.saveBtn.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
            }

        }

    private val diffCallback = object : DiffUtil.ItemCallback<Astro>() {
        override fun areItemsTheSame(oldItem: Astro, newItem: Astro): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Astro, newItem: Astro): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var astros: List<Astro>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AstroViewHolder {
        return AstroViewHolder(
            AstroItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            mListener
        )
    }

    override fun onBindViewHolder(holder: AstroViewHolder, position: Int) {
        holder.binding.apply {
            val astro = astros[position]
            Picasso.get().load(astro.hdurl).into(astroImage)
            astroTitle.text = astro.title
            astroDescription.text = astro.explanation
        }
    }

    override fun getItemCount(): Int {
        return astros.size
    }

}