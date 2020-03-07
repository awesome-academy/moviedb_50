package com.sun_asterisk.moviedb_50.data.source.remote

interface OnFetchDataJsonListener<T> {
    fun onSuccess(data: T?)
    fun onError(e: Exception)
}
