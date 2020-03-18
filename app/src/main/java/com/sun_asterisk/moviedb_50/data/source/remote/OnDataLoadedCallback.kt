package com.sun_asterisk.moviedb_50.data.source.remote

interface OnDataLoadedCallback<T> {
    fun onSuccess(data: T?)
    fun onError(e: Exception)
}
