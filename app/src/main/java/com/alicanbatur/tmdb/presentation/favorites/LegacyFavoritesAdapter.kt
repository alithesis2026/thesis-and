package com.alicanbatur.tmdb.presentation.favorites

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alicanbatur.tmdb.domain.model.Movie

class LegacyFavoritesAdapter(
    private val movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<LegacyFavoritesAdapter.ViewHolder>() {

    class ViewHolder(val titleView: TextView) : RecyclerView.ViewHolder(titleView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val titleView = TextView(parent.context)
        return ViewHolder(titleView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleView.text = movie.title
        holder.titleView.setOnClickListener { onMovieClick(movie) }
    }

    override fun getItemCount(): Int = movies.size
}
