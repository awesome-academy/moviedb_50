package com.sun_asterisk.moviedb_50.data.source.local.base

interface LocalDataHandler<P, T> {
    fun execute(params: P): T
}
