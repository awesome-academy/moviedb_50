package com.sun_asterisk.moviedb_50.utils

import com.sun_asterisk.moviedb_50.BuildConfig

object Constant {
    const val BASE_URL = "https://api.themoviedb.org/3"
    const val BASE_MOVIE = "/movie/"
    const val BASE_APPEND = "&append_to_response="
    const val BASE_CREDITS = "credits"
    const val BASE_VIDEO = ",videos"
    const val BASE_GENRES_LIST = "/genre/movie/list"
    const val BASE_MOVIE_BY_ID = "/discover/movie"
    const val BASE_GENRES_ID = "&with_genres="
    const val BASE_NOW_PLAYING = "/movie/now_playing"
    const val BASE_POPULAR = "/movie/popular"
    const val BASE_TOP_RATE = "/movie/top_rated"
    const val BASE_UPCOMING = "/movie/upcoming"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
    const val BASE_LANGUAGE = "&language=en-US"
    const val BASE_API_KEY = "?api_key=" + BuildConfig.API_KEY
    const val BASE_PAGE = "?page="
    const val BASE_PAGE_DEFAULT = 1
    const val BASE_GENRES_ID_DEFAULT = 1
    const val BASE_DELAY_SLIDE = 4000L
    const val BASE_TIME_OUT = 15000
    const val BASE_METHOD_GET = "GET"
    const val BASE_MOVIE_ID = "ID"
    const val BASE_URL_IMAGE_2="https://img.youtube.com/vi/"
    const val BASE_URL_IMAGE_DEFAULT="/hqdefault.jpg"
}
