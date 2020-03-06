package com.sun_asterisk.moviedb_50.data.source.remote

import java.lang.Exception

interface OnFetchDataJsonListener<T> {
    fun onSuccess(data: MutableList<T>?)
    fun onError(e: Exception)
}
