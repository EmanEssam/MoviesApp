package com.example.moviesapp.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviesapp.R
import com.example.moviesapp.view.adapters.MovieListAdapter
import com.example.moviesapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
    private val adapter = MovieListAdapter(mutableListOf(), onItemClicked = {
        goToMovieDetailsActivity(it.id!!)
    })
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        moviesRecyclerView.adapter = adapter
        showLoading()
        searchBar.editText!!.doAfterTextChanged {
            if (searchBar.editText!!.text.isNotEmpty())
                searchMovie()
        }
        viewModel.getSavedMovies().observe(this, Observer { movies ->
            hideLoading()
            movies?.let {
                adapter.setMovies(movies)
                hideEmptyView()
            }
            if (movies.isNullOrEmpty()) showEmptyView()
        })
    }

    private fun searchMovie() {
        showLoading()
        viewModel.searchMovie(searchBar.editText!!.text.toString())
            .observe(this, Observer { movies ->
                hideLoading()
                adapter.setMovies(movies!!)
            })
    }

    private fun showLoading() {
        moviesRecyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        moviesRecyclerView.isEnabled = true
        progressBar.visibility = View.GONE
    }

    private fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }

    private fun hideEmptyView() {
        emptyView.visibility = View.GONE

    }

    private fun deleteMoviesClicked() {
        for (movie in adapter.selectedMovies) {
            viewModel.deleteSavedMovies(movie)
        }
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    private fun goToMovieDetailsActivity(movieId: Int) =
        startActivity<MovieDetailsActivity>("movieId" to movieId)

}
