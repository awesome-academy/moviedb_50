package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.source.MovieDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GenresResponseHandler
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.GetDataFromUrlAsync
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.MovieDetailsResponseHandler
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.MoviesResponseHandler
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse
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

    override fun getMovies(
        type: String,
        query: String,
        page: Int,
        listener: OnFetchDataJsonListener<MoviesResponse>
    ) {

        val url = Constant.BASE_URL +
                when (type) {
                    Constant.BASE_GENRES_ID -> Constant.BASE_DISCOVER_MOVIE
                    Constant.BASE_CAST_ID -> Constant.BASE_DISCOVER_MOVIE
                    Constant.BASE_PRODUCE_ID -> Constant.BASE_DISCOVER_MOVIE
                    else -> type
                } +
                Constant.BASE_API_KEY +
                Constant.BASE_LANGUAGE +
                if (page > 0) {
                    Constant.BASE_PAGE +
                            page
                } else {
                    ""
                } +
                when (type) {
                    Constant.BASE_GENRES_ID -> type + query
                    Constant.BASE_CAST_ID -> type + query
                    Constant.BASE_PRODUCE_ID -> type + query
                    Constant.BASE_SEARCH -> Constant.BASE_QUERY + query
                    else -> ""
                }
        GetDataFromUrlAsync(MoviesResponseHandler(), listener).execute(url)
    }

    override fun getMovieDetails(
        movieID: Int,
        listener: OnFetchDataJsonListener<MovieDetailsResponse>
    ) {
        val url = Constant.BASE_URL +
                Constant.BASE_MOVIE +
                movieID +
                Constant.BASE_API_KEY +
                Constant.BASE_LANGUAGE +
                Constant.BASE_APPEND +
                Constant.BASE_CREDITS +
                Constant.BASE_VIDEO
        GetDataFromUrlAsync(MovieDetailsResponseHandler(), listener).execute(url)
    }

    companion object {
        private var instance: MovieRemoteDataSource? = null
        fun getInstance() =
            instance ?: MovieRemoteDataSource().also { instance = it }
    }
}
