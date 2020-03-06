package com.sun_asterisk.moviedb_50.data.source.local

import com.sun_asterisk.moviedb_50.data.source.DataSource

object LocalDataSource : DataSource.LocalDataSource {
    private var sInstance: LocalDataSource? = null
    fun getsInstance(): LocalDataSource? {
        if (sInstance == null) {
            sInstance = LocalDataSource
        }
        return sInstance
    }
}
