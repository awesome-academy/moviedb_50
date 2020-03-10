package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.model.GenresResponse
import com.sun_asterisk.moviedb_50.data.model.MoviesResponse
import com.sun_asterisk.moviedb_50.data.source.DataSource
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GenresResponseHandler
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GetDataFromUrlAsync
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.MoviesResponseHandler
import com.sun_asterisk.moviedb_50.utils.Constant

class RemoteDataSource : DataSource.RemoteDataSource {

    override fun getGenres(listener: OnFetchDataJsonListener<GenresResponse>) {
        val url =
            Constant.BASE_URL +
                    Constant.BASE_GENRES_LIST +
                    Constant.BASE_API_KEY +
                    Constant.BASE_LANGUAGE
        GetDataFromUrlAsync(GenresResponseHandler(),listener).execute(url)
    }

    override fun getMovie(
        type: String, page: Int,
        listener: OnFetchDataJsonListener<MoviesResponse>
    ) {
        val url = Constant.BASE_URL +
                type +
                Constant.BASE_API_KEY +
                Constant.BASE_LANGUAGE +
                Constant.BASE_PAGE +
                page
        GetDataFromUrlAsync(MoviesResponseHandler(),listener).execute(url)
    }

    companion object {
        private var instance: RemoteDataSource? = null
        fun getInstance() = instance ?: RemoteDataSource().also { instance = it }
    }
}
