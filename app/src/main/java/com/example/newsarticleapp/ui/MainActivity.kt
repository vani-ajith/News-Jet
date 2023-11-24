package com.example.newsarticleapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsarticleapp.R
import com.example.newsarticleapp.controller.NewsApiTask
import com.example.newsarticleapp.model.Article

class MainActivity : AppCompatActivity() {

    private lateinit var newsArticleAdapter : NewsArticleAdapter
    private lateinit var recyclerViewArticle : RecyclerView
    private lateinit var spinner : Spinner
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // id's of views
        recyclerViewArticle = findViewById(R.id.rv_article_list);
        spinner = findViewById(R.id.spinner_homepage_dropdown)


        /**
         *      Spinner used to list down the options to filter the new articles as per date
         */
        val options = arrayOf("New-Old","Old-New")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,options)
        spinner.adapter = arrayAdapter



        newsArticleAdapter = NewsArticleAdapter { url ->
            // Handle click event ..opens, the article in a browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        recyclerViewArticle.layoutManager = LinearLayoutManager(this)
        recyclerViewArticle.adapter = newsArticleAdapter

        //fetch news data
        val newsApiUrl = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"
        val newsApiTask = NewsApiTask { articles ->
            articles?.let {
                newsArticleAdapter.setData(it)
            }
        }
        newsApiTask.execute(newsApiUrl)

    }
}