package com.example.moviesapp.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviesapp.R
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.net.RetrofitClient
import com.example.moviesapp.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_movie_details.*
import kotlinx.android.synthetic.main.item_movie_search.searchImageView
import kotlinx.android.synthetic.main.item_movie_search.searchReleaseDateTextView
import kotlinx.android.synthetic.main.item_movie_search.searchTitleTextView

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(main_toolbar)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val movieId = intent.getIntExtra("movieId", 0)
        viewModel.getSavedMovie(movieId).observe(this, Observer {
            fillMovieData(it)
        })
    }

    private fun fillMovieData(movie: Movie) {
        title = movie.title
        searchTitleTextView.text = movie.title
        movieOverviewTextView.text = movie.overview
        searchReleaseDateTextView.text = movie.releaseDate
        if (movie.posterPath != null)
            Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movie.posterPath)
                .into(searchImageView)
        else {
            moviePoster.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_local_movies_gray,
                    null
                )
            )
        }
        if (movie.backdropPath != null)
            Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movie.backdropPath)
                .into(moviePoster)
        else {
            moviePoster.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_local_movies_gray,
                    null
                )
            )
        }
    }
}
