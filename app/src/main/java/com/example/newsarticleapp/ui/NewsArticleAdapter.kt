package com.example.newsarticleapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.newsarticleapp.R
import com.example.newsarticleapp.model.Article
import com.squareup.picasso.Picasso

class NewsArticleAdapter(private val onItemClick: (String) -> Unit) : Adapter<NewsArticleAdapter.Viewholder>() {

    private val newsArticleList = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_news_article,parent,false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        holder.article_headline.text = newsArticleList[position].title;
        holder.article_datePosted.text = newsArticleList[position].publishedAt;
        holder.article_author.text = newsArticleList[position].author;
        holder.article_description.text = newsArticleList[position].description;

        Picasso.get().load(newsArticleList[position].urlToImage).error(R.drawable.ic_error_image).into(holder.article_image)
        holder.article_headline.setOnClickListener { onItemClick.invoke(newsArticleList[position].url) }
    }

    override fun getItemCount(): Int {
        return newsArticleList.size
    }

    fun setData(newArticles: List<Article>) {
        newsArticleList.clear()
        newsArticleList.addAll(newArticles)
        notifyDataSetChanged()
    }

    class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val article_headline : TextView = itemView.findViewById(R.id.textview_article_headline)
        val article_image : ImageView = itemView.findViewById(R.id.imageview_article_image)
        val article_datePosted : TextView = itemView.findViewById(R.id.textview_article_date_posted)
        val article_author : TextView = itemView.findViewById(R.id.textview_article_author)
        val article_description : TextView = itemView.findViewById(R.id.textview_article_description)

    }
}