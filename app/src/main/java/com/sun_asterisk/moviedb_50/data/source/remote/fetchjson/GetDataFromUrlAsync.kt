package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import android.os.AsyncTask
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import org.json.JSONException

class GetDataFromUrlAsync<T>(
    private val handler: ParseDataWithJson<T>,
    private val listener: OnDataLoadedCallback<T>
) : AsyncTask<String?, Void?, T?>() {

    override fun onPostExecute(result: T?) {
        super.onPostExecute(result)
        try {
            listener.onSuccess(result)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun doInBackground(vararg params: String?): T? {
        return try {
            handler.getJsonFromUrl(params[0])
        } catch (e: Exception) {
            listener.onError(e)
            null
        }
    }
}
