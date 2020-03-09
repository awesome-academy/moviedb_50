package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.DataSource
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GetListGenresAsync
import com.sun_asterisk.moviedb_50.utils.Constant

class RemoteDataSource : DataSource.RemoteDataSource {

    override fun getGenres(onFetchDataJsonListener: OnFetchDataJsonListener<MutableList<Genres>>) {
        val url =
            Constant.BASE_URL +
                Constant.BASE_GENRES_LIST +
                Constant.BASE_API_KEY +
                Constant.BASE_LANGUAGE
        GetListGenresAsync(onFetchDataJsonListener).execute(url)
    }

    override fun getMovie(type: String,
                          onFetchDataJsonListener: OnFetchDataJsonListener<MutableList<Movie>>) {
    }

    companion object {
        private var instance: RemoteDataSource? = null
        fun getInstance() = instance ?: RemoteDataSource().also { instance = it }
    }
}
