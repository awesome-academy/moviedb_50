package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import android.os.AsyncTask
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import org.json.JSONException
import org.json.JSONObject

class GetListGenresAsync(private val listener: OnFetchDataJsonListener<MutableList<Genres>>) :
    AsyncTask<String?, Void?, MutableList<Genres>?>() {

    override fun onPostExecute(result: MutableList<Genres>?) {
        super.onPostExecute(result)
        try {
            listener.onSuccess(result)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun doInBackground(vararg params: String?): MutableList<Genres>? {
        try {
            val parseDataWithJson = ParseDataWithJson()
            val data = params[0]?.let { parseDataWithJson.getJsonFromUrl(it) }
            val jsonObject = data?.let { JSONObject(data) }
            return jsonObject?.let { ParseJsonToGenres().parseJsonToData(it) }
        } catch (e: Exception) {
            listener.onError(e)
        }
        return null
    }
}
