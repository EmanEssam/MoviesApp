package com.example.moviesapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.net.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_main.view.*
import java.util.*

class MovieListAdapter(private val movies: MutableList<Movie>, val onItemClicked: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_main, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int = movies.size ?: 0

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setMovies(movieList: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()
    }

    inner class MovieHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) = with(view) {
            movieTitleTextView.text = movie.title
            movieReleaseDateTextView.text = movie.releaseDate
            if (movie.posterPath != null)
                Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movie.posterPath)
                    .into(movieImageView)
            else {
                movieImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_local_movies_gray,
                        null
                    )
                )
            }
            itemView.setOnClickListener {
                onItemClicked.invoke(movie)
            }
        }
    }
}