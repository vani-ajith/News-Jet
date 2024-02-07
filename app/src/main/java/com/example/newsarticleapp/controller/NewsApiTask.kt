package com.example.newsarticleapp.controller

import android.os.AsyncTask
import com.example.newsarticleapp.model.Article
import com.example.newsarticleapp.model.NewsResponse
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NewsApiTask(private val callback: (List<Article>?) -> Unit) : AsyncTask<String, Void, List<Article>>() {
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: String?): List<Article>? {

        val apiUrl = params[0]

        try {
            val url = URL(apiUrl)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

            urlConnection.requestMethod = "GET"
            urlConnection.connect()

            val inputStream = urlConnection.inputStream
            val reader = InputStreamReader(inputStream,"UTF-8")
            val request = Gson().fromJson(reader,NewsResponse::class.java)
                //updateUI(request)
            inputStream.close()
            reader.close()

            return request?.articles

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


    override fun onPostExecute(result: List<Article>) {
            callback.invoke(result)
    }


}