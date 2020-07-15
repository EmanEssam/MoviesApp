package com.example.moviesapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesapp.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<Movie>)

    @Query("select * from movie")
    fun getAll(): LiveData<List<Movie>>

    @Query("select * from movie where id = :id")
    fun getMovieById(id: Int?): LiveData<Movie>

    @Query("delete from movie where watched = :watched")
    fun deleteMovies(watched: Boolean)

    @Update
    fun updateMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id = :id")
    fun delete(id: Int?)

}