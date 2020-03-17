package com.sun_asterisk.moviedb_50.data.model

import org.json.JSONObject

data class Cast(
    val castId: Int,
    val castName: String,
    val castProfilePath: String
) {
    constructor(castJson: JSONObject) : this(
        castId = castJson.optInt(CastEntry.ID),
        castName = castJson.optString(CastEntry.NAME),
        castProfilePath = castJson.optString(CastEntry.PROFILE_PATH)
    )

    object CastEntry {
        const val CREDITS = "credits"
        const val CAST = "cast"
        const val ID = "id"
        const val NAME = "name"
        const val PROFILE_PATH = "profile_path"
    }
}
