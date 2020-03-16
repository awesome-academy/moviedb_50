package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.utils.Constant
import org.json.JSONException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

interface ParseDataWithJson<T> {

    @Throws(JSONException::class)
    fun parseToObject(jsonData: String): T

    @Throws(Exception::class)
    fun getJsonFromUrl(urlString: String?): T {
        val responseData: T?
        val url = URL(urlString)
        val httpURLConnection: HttpURLConnection =
            (url.openConnection() as HttpURLConnection).apply {
                connectTimeout = Constant.BASE_TIME_OUT
                readTimeout = Constant.BASE_TIME_OUT
                requestMethod = Constant.BASE_METHOD_GET
                doOutput = true
                connect()
            }
        val bufferedReader =
            BufferedReader(InputStreamReader(url.openStream()))
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        httpURLConnection.disconnect()
        responseData = parseToObject(stringBuilder.toString())
        return responseData
    }
}
