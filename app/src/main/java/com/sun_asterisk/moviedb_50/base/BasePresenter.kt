package com.sun_asterisk.moviedb_50.base

interface BasePresenter<T> {
    fun onStart()
    fun onStop()
    fun setView(view: T)
}
