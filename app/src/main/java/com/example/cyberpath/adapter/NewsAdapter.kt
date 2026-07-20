package com.example.cyberpath.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cyberpath.NewsDetailActivity
import com.example.cyberpath.R
import com.example.cyberpath.model.NewsArticle

class NewsAdapter(
    private val newsList: List<NewsArticle>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgNews: ImageView = itemView.findViewById(R.id.imgNews)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvSource: TextView = itemView.findViewById(R.id.tvSource)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val article = newsList[position]

        holder.tvTitle.text = article.title ?: "No Title"

        if (!article.description.isNullOrBlank()) {

            holder.tvDescription.visibility = View.VISIBLE
            holder.tvDescription.text = article.description

        } else {

            holder.tvDescription.visibility = View.GONE

        }

        holder.tvSource.text =
            when {
                !article.source.isNullOrBlank() -> article.source
                !article.author.isNullOrBlank() -> article.author
                else -> "Cyber News"
            }

        holder.tvDate.text =
            article.published?.take(10) ?: ""

        if (!article.image.isNullOrBlank()) {

            holder.imgNews.visibility = View.VISIBLE

            Glide.with(holder.itemView.context)
                .load(article.image)
                .centerCrop()
                .placeholder(R.drawable.news_placeholder)
                .error(R.drawable.news_placeholder)
                .into(holder.imgNews)

        } else {

            holder.imgNews.visibility = View.GONE

        }

        holder.itemView.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                NewsDetailActivity::class.java
            )

            intent.putExtra("image", article.image)
            intent.putExtra("title", article.title)
            intent.putExtra("author", article.author)
            intent.putExtra("date", article.published)
            intent.putExtra("description", article.description)
            intent.putExtra("url", article.url)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = newsList.size
}