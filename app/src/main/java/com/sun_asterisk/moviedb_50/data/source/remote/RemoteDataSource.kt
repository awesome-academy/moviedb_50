package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.DataSource
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GetDataJson


class RemoteDataSource : DataSource.RemoteDataSource {

    override fun getGenres(onFetchDataJsonListener: OnFetchDataJsonListener<Genres>?) {
        val getDataJson =
            GetDataJson(
                onFetchDataJsonListener
            )
        getDataJson.genres()
    }

    override fun getMovie(onFetchDataJsonListener: OnFetchDataJsonListener<Movie>?, type: String) {
        TODO("Not yet implemented")
    }


    companion object {
        private var sInstance: RemoteDataSource? = null
        fun getsInstance(): RemoteDataSource? {
            if (sInstance == null) {
                sInstance =
                    RemoteDataSource()
            }
            return sInstance
        }
    }
}
