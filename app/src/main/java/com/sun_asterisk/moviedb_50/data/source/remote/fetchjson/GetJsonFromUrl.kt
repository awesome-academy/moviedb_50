package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import android.os.AsyncTask
import android.util.Log
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import org.json.JSONException
import org.json.JSONObject

class GetJsonFromUrl(private val listener: OnFetchDataJsonListener<Genres>?) :
    AsyncTask<String?, Void?, String?>() {

    override fun onPostExecute(data: String?) {
        super.onPostExecute(data)
        if (data != null) {
            try {
                val jsonObject = JSONObject(data)
                listener?.onSuccess(ParseJsonToGenres().parseJsonToData(jsonObject))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    override fun doInBackground(vararg params: String?): String? {
        var data: String? = ""
        try {
            val parseDataWithJson = ParseDataWithJson()
            data = parseDataWithJson.getJsonFromUrl(params[0])
        } catch (e: Exception) {
            Log.i("onError", e.toString())
            listener?.onError(e)
        }
        return data
    }
}
