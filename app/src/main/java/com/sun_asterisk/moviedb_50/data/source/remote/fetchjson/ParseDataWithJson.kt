package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.utils.Constant
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ParseDataWithJson {

    @Throws(Exception::class)
    fun getJsonFromUrl(urlString: String?): String {
        val url = URL(urlString)
        val httpURLConnection: HttpURLConnection = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = Constant.TIME_OUT
            readTimeout = Constant.TIME_OUT
            requestMethod = Constant.METHOD_GET
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
        return stringBuilder.toString()
    }
}
