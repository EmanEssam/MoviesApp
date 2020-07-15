package com.example.moviesapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.db
import com.example.moviesapp.data.db.MovieDao
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.model.MoviesResponse
import com.example.moviesapp.data.net.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MovieRepositoryImpl : MovieRepository {

    private val movieDao: MovieDao = db.movieDao()
    private val retrofitClient = RetrofitClient()
    private val allMovies: LiveData<List<Movie>>

    init {
        allMovies = movieDao.getAll()
    }

    override fun deleteMovie(movie: Movie) {
        thread {
            db.movieDao().delete(movie.id)
        }
    }

    override fun getSavedMovies() = allMovies
    override fun getMovieById(id: Int): LiveData<Movie> = movieDao.getMovieById(id)
    override fun saveMovie(movie: Movie) {
        thread {
            movieDao.insert(movie)
        }
    }

    override fun saveAll(movieList: List<Movie>) {
        thread {
            movieDao.insertAllMovies(movieList)
        }
    }

    override fun searchMovies(query: String): LiveData<List<Movie>?> {

        val data = MutableLiveData<List<Movie>>()
        retrofitClient.searchMovies(query).enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                data.value = null
                Log.d(this.javaClass.simpleName, "Failure")
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful)
                data.value = response.body()?.results
                saveAll(response.body()!!.results!!)
                Log.d(this.javaClass.simpleName, "Response: ${response.body()?.results}")
            }
        })
        return data
    }
    override fun getMoviesFromApi(): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()

        retrofitClient.getMoviesFromApi().enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                data.value = null
                Log.d(this.javaClass.simpleName, "Failure")
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                data.value = response.body()?.results
                Log.d(this.javaClass.simpleName, "Response: ${response.body()?.results}")
            }
        })
        return data
    }
}