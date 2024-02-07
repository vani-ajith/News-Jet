package com.example.newsarticleapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsarticleapp.controller.NewsApiTask
import com.example.newsarticleapp.databinding.ActivityMainBinding
import com.example.newsarticleapp.model.Article

class MainActivity : AppCompatActivity() {

    private var articleList: List<Article> = emptyList()
    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var newsArticleAdapter : NewsArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)


        newsArticleAdapter = NewsArticleAdapter { url ->
            // Handle click event ..opens, the article in a browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        mainBinding.rvArticleList.layoutManager = LinearLayoutManager(this)

        loadProgressBar()
        fetchCurrentData()
        spinner()


    }

    private fun spinner() {
        /**
         *      Spinner used to list down the options to filter the new articles as per date
         */
        val options = arrayOf("New-Old", "Old-New")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        mainBinding.spinnerFilter.adapter = arrayAdapter

        mainBinding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = options[position]
                    val sortedArticles = sortArticlesByDate(articleList, selectedItem)
                    newsArticleAdapter.setData(sortedArticles)
                    // Updating the spinner text according to the selected item
                    mainBinding.filterLabel.text = selectedItem
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when nothing is selected
                }
            }
    }

    fun sortArticlesByDate(articles: List<Article>, sortOrder: String): List<Article> {
        return when (sortOrder) {
            "new - old" -> {
                articles.sortedBy { article -> article.publishedAt }
            }
            "old - new" -> {
                articles.sortedByDescending { article -> article.publishedAt }
            }
            else -> articles // Default to original order
        }
    }

    private fun loadProgressBar(){
        mainBinding.progressBar.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            mainBinding.progressBar.visibility = View.GONE
        }, 3000)
    }

    private fun fetchCurrentData(){
        // Fetch news data
        val newsApiUrl = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"
        NewsApiTask { articles ->
            runOnUiThread {
                articles?.let {
                    // Set adapter before setting data
                    mainBinding.rvArticleList.adapter = newsArticleAdapter
                    // Set data to adapter
                    newsArticleAdapter.setData(it)
                }
            }
        }.execute(newsApiUrl)
    }
}