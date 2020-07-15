package com.example.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.MovieRepositoryImpl
import com.example.moviesapp.data.model.Movie

class MainViewModel(private val repository: MovieRepository = MovieRepositoryImpl()) : ViewModel() {

    private val allMovies = MediatorLiveData<List<Movie>>()

    init {
        getAllMovies()
    }
    fun getSavedMovies() = allMovies
    fun getSavedMovie(id: Int): LiveData<Movie> = repository.getMovieById(id)


    private fun getAllMovies() {
        allMovies.addSource(repository.getMoviesFromApi()) {
            if (it != null) {
                allMovies.postValue(it)
                repository.saveAll(it)
            } else {
                allMovies.addSource(repository.getSavedMovies()) {
                    allMovies.postValue(it)
                }
            }
        }
    }

    fun searchMovie(query: String): LiveData<List<Movie>?> {
        return repository.searchMovies(query)
    }

    fun deleteSavedMovies(movie: Movie) {
        repository.deleteMovie(movie)
    }
}