package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.DataSource


class Repository private constructor(
    private val mRemoteDataSource: DataSource.RemoteDataSource?,
    private val mLocalDataSource: DataSource.LocalDataSource?
) {
    fun getGenres(onFetchDataJsonListener: OnFetchDataJsonListener<Genres>?) {
        mRemoteDataSource?.getGenres(onFetchDataJsonListener)
    }

    fun getMovies(onFetchDataJsonListener: OnFetchDataJsonListener<Movie>?, type: String) {
        mRemoteDataSource?.getMovie(onFetchDataJsonListener, type)
    }

    companion object {
        private var sInstance: Repository? = null
        fun getsInstance(
            remoteDataSource: RemoteDataSource?,
            localDataSource: DataSource.LocalDataSource?
        ): Repository? {
            if (sInstance == null) {
                sInstance =
                    Repository(remoteDataSource, localDataSource)
            }
            return sInstance
        }
    }
}
