package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import android.os.AsyncTask
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import org.json.JSONException
import org.json.JSONObject

class GetJsonFromUrl(private val listener: OnFetchDataJsonListener<MutableList<Genres>>) :
    AsyncTask<String?, Void?, String?>() {

    override fun onPostExecute(data: String?) {
        super.onPostExecute(data)
        try {
            val jsonObject = data?.let { JSONObject(data) }
            jsonObject?.let { listener.onSuccess(ParseJsonToGenres().parseJsonToData(it)) }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun doInBackground(vararg params: String?): String? {
        try {
            val parseDataWithJson = ParseDataWithJson()
            return params[0]?.let { parseDataWithJson.getJsonFromUrl(it) }
        } catch (e: Exception) {
            listener.onError(e)
        }
        return null
    }
}
