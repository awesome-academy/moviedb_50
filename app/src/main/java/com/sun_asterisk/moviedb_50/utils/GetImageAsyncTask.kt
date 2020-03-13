package com.sun_asterisk.moviedb_50.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class GetImageAsyncTask(private var listener: OnFetchImageListener) :
    AsyncTask<String?, Void?, Bitmap?>() {

    override fun doInBackground(vararg params: String?): Bitmap? {
        return params[0]?.let { loadImage(it) }
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        super.onPostExecute(bitmap)
        try {
            listener.onImageLoaded(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadImage(string: String): Bitmap? {
        return try {
            val url = URL(string)
            val httpUrlConnection = (url.openConnection() as HttpURLConnection).apply {
                doInput = true
            }
            httpUrlConnection.connect()
            var inputStream: InputStream? = null
            if (httpUrlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpUrlConnection.inputStream
            }
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            listener.onImageError(e)
            null
        }
    }
}
