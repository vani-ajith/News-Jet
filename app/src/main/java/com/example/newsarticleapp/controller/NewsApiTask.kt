package com.example.newsarticleapp.controller

import android.os.AsyncTask
import com.example.newsarticleapp.model.Article
import com.example.newsarticleapp.model.NewsResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NewsApiTask(private val callback: (List<Article>?) -> Unit) : AsyncTask<String, Void, String>() {


    override fun doInBackground(vararg params: String?): String {

        val apiUrl = params[0]

        try {
            val url = URL(apiUrl)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

            try {
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }

                return stringBuilder.toString()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    override fun onPostExecute(result: String?) {
        if (!result.isNullOrBlank()) {
            // Parse JSON and convert it to a list of articles
            val newsResponse = Gson().fromJson(result, NewsResponse::class.java)
            callback.invoke(newsResponse?.article)
        } else {
            callback.invoke(null)
        }
    }


}