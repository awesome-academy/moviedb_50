package com.sun_asterisk.moviedb_50.utils

import com.sun_asterisk.moviedb_50.BuildConfig

object Constant {
    const val BASE_URL = "https://api.themoviedb.org/3"
    const val BASE_MOVIE = "/movie/"
    const val BASE_APPEND = "&append_to_response="
    const val BASE_CREDITS = "credits"
    const val BASE_VIDEO = ",videos"
    const val BASE_GENRES_LIST = "/genre/movie/list"
    const val BASE_DISCOVER_MOVIE = "/discover/movie"
    const val BASE_GENRES_ID = "&with_genres="
    const val BASE_CAST_ID = "&with_cast="
    const val BASE_PRODUCE_ID = "&with_companies="
    const val BASE_NOW_PLAYING = "/movie/now_playing"
    const val BASE_POPULAR = "/movie/popular"
    const val BASE_TOP_RATE = "/movie/top_rated"
    const val BASE_UPCOMING = "/movie/upcoming"
    const val BASE_DATABASE_NAME = "favorites.db"
    const val BASE_DATABASE_VERSION = 1
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
    const val BASE_LANGUAGE = "&language=en-US"
    const val BASE_API_KEY = "?api_key=" + BuildConfig.API_KEY
    const val BASE_PAGE = "&page="
    const val BASE_PAGE_DEFAULT = 1
    const val BASE_QUERY_DEFAULT = "1"
    const val BASE_DELAY_SLIDE = 4000L
    const val BASE_TIME_OUT = 15000
    const val BASE_METHOD_GET = "GET"
    const val BASE_URL_IMAGE_2 = "https://img.youtube.com/vi/"
    const val BASE_URL_IMAGE_DEFAULT = "/hqdefault.jpg"
    const val BASE_TYPE = "type"
    const val BASE_VALUE = "value"
    const val BASE_TITLE = "title"
    const val BASE_SEARCH = "/search/movie"
    const val BASE_QUERY = "&query="
    const val BASE_NOTIFY_ADD_FAVORITE_SUCCESS = "add success"
    const val BASE_NOTIFY_ADD_FAVORITE_ERROR = "add error"
    const val BASE_NOTIFY_DELETE_FAVORITE_SUCCESS = "delete success"
    const val BASE_NOTIFY_DELETE_FAVORITE_ERROR = "delete error"
}
