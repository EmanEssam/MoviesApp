package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.data.db.MovieDatabase

lateinit var db: MovieDatabase

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        db = MovieDatabase.getInstance(this)
        INSTANCE = this
    }
}