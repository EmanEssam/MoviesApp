package com.example.moviesapp.data.net

import com.example.moviesapp.data.model.MoviesResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val moviesApi: MoviesApi

    companion object {
        private const val API_KEY = "ee278e0e6845dcdfb733e43a139da775"
        private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
        const val TMDB_IMAGEURL = "https://image.tmdb.org/t/p/w500/"
    }

    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    fun searchMovies(query: String): Call<MoviesResponse> {
        return moviesApi.searchMovie(API_KEY, query)
    }

    fun getMoviesFromApi(): Call<MoviesResponse> {
        return moviesApi.getMovies(API_KEY)
    }

}