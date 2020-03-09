package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import com.sun_asterisk.moviedb_50.utils.Constant

class GetDataJson(
    private val listener: OnFetchDataJsonListener<MutableList<Genres>>
) {
    fun genres() {
        val url =
            Constant.BASE_URL + Constant.BASE_GENRES_LIST + Constant.BASE_API_KEY + Constant.BASE_LANGUAGE
        GetJsonFromUrl(listener).execute(url)
    }
}
