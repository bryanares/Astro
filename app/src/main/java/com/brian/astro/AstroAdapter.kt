package com.brian.astro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.brian.astro.databinding.AstroItemBinding
import com.squareup.picasso.Picasso

class AstroAdapter() : RecyclerView.Adapter<AstroAdapter.AstroViewHolder>() {

    inner class AstroViewHolder(val binding: AstroItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Astro>(){
        override fun areItemsTheSame(oldItem: Astro, newItem: Astro): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Astro, newItem: Astro): Boolean {
            return  oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var astros : List<Astro>
    get() = differ.currentList
    set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AstroViewHolder {
        return AstroViewHolder(AstroItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
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