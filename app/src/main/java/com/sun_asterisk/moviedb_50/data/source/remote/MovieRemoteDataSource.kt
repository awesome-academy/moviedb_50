package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.source.MovieDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GenresResponseHandler
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GetDataFromUrlAsync
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.MoviesResponseHandler
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse
import com.sun_asterisk.moviedb_50.utils.Constant

class MovieRemoteDataSource : MovieDataSource.RemoteDataSource {

    override fun getGenres(listener: OnFetchDataJsonListener<GenresResponse>) {
        val url =
            Constant.BASE_URL +
                    Constant.BASE_GENRES_LIST +
                    Constant.BASE_API_KEY +
                    Constant.BASE_LANGUAGE
        GetDataFromUrlAsync(GenresResponseHandler(), listener).execute(url)
    }

    override fun getMovie(
        type: String, page: Int,
        genresID: Int,
        listener: OnFetchDataJsonListener<MoviesResponse>
    ) {

        val url = Constant.BASE_URL +
                type +
                Constant.BASE_API_KEY +
                Constant.BASE_LANGUAGE +
                Constant.BASE_PAGE +
                page +
                if (type == Constant.BASE_MOVIE_BY_ID)
                    Constant.BASE_GENRES_ID + genresID
                else ""
        GetDataFromUrlAsync(MoviesResponseHandler(), listener).execute(url)
    }

    companion object {
        private var instance: MovieRemoteDataSource? = null
        fun getInstance() =
            instance ?: MovieRemoteDataSource().also { instance = it }
    }
}
