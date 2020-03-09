package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.DataSource
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GetDataJson

class RemoteDataSource : DataSource.RemoteDataSource {

    override fun getGenres(onFetchDataJsonListener: OnFetchDataJsonListener<MutableList<Genres>>) {
        GetDataJson(onFetchDataJsonListener).genres()
    }

    override fun getMovie(type: String, onFetchDataJsonListener: OnFetchDataJsonListener<MutableList<Movie>>) {
    }

    companion object {
        private var instance: RemoteDataSource? = null
        fun getInstance() = instance ?: RemoteDataSource().also { instance = it }
    }
}
