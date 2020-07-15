package com.example.moviesapp.data

import androidx.lifecycle.LiveData
import com.example.moviesapp.data.model.Movie

interface MovieRepository {

    fun getSavedMovies(): LiveData<List<Movie>>
    fun getMovieById(id: Int): LiveData<Movie>

    fun saveMovie(movie: Movie)

    fun saveAll(movieList: List<Movie>)

    fun deleteMovie(movie: Movie)

    fun searchMovies(query: String): LiveData<List<Movie>?>
    fun getMoviesFromApi(): LiveData<List<Movie>>

}