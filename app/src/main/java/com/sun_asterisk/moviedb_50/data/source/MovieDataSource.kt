package com.sun_asterisk.moviedb_50.data.source

import com.sun_asterisk.moviedb_50.data.model.Category
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse

interface MovieDataSource {
    /**
     * Local
     */
    interface Local {
        fun getCategories(listener: OnDataLoadedCallback<List<Category>>)
    }

    /**
     * Remote
     */
    interface Remote {

        fun getGenres(listener: OnDataLoadedCallback<GenresResponse>)

        fun getMovies(
            type: String,
            query: String,
            page: Int,
            listener: OnDataLoadedCallback<MoviesResponse>
        )

        fun getMovieDetails(movieID: Int, listener:
            OnDataLoadedCallback<MovieDetailsResponse>
        )
    }
}
