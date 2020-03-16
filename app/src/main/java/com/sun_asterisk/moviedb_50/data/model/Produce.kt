package com.sun_asterisk.moviedb_50.data.model

import org.json.JSONObject

data class Produce(
    val produceID: Int,
    val produceLogo: String,
    val produceName: String,
    val produceCountry: String
) {
    constructor(produceJson: JSONObject) : this(
        produceID = produceJson.optInt(ProduceEntry.ID),
        produceLogo = produceJson.optString(ProduceEntry.LOGO),
        produceName = produceJson.optString(ProduceEntry.NAME),
        produceCountry = produceJson.optString(ProduceEntry.COUNTRY)
    )

    object ProduceEntry {
        const val PRODUCES = "production_companies"
        const val ID = "id"
        const val LOGO = "logo_path"
        const val NAME = "name"
        const val COUNTRY = "origin_country"
    }
}
