package com.example.cyberpath

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var imgNews: ImageView
    private lateinit var txtTitle: TextView
    private lateinit var txtAuthor: TextView
    private lateinit var txtDate: TextView
    private lateinit var txtDescription: TextView
    private lateinit var btnReadMore: MaterialButton

    private var articleUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        initViews()

        loadNews()

        btnBack.setOnClickListener {
            finish()
        }

        btnReadMore.setOnClickListener {

            if (articleUrl.isNotEmpty()) {

                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(articleUrl)
                    )
                )

            }

        }

    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)
        imgNews = findViewById(R.id.imgNews)
        txtTitle = findViewById(R.id.txtTitle)
        txtAuthor = findViewById(R.id.txtAuthor)
        txtDate = findViewById(R.id.txtDate)
        txtDescription = findViewById(R.id.txtDescription)
        btnReadMore = findViewById(R.id.btnReadMore)

    }

    private fun loadNews() {

        val image = intent.getStringExtra("image")
        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val date = intent.getStringExtra("date")
        val description = intent.getStringExtra("description")

        articleUrl = intent.getStringExtra("url") ?: ""

        txtTitle.text = title ?: "No Title"

        txtAuthor.text =
            if (!author.isNullOrBlank())
                author
            else
                "CyberPath News"

        txtDate.text = formatDate(date)

        txtDescription.text =
            if (!description.isNullOrBlank())
                description
            else
                "No description available."

        if (!image.isNullOrBlank()) {

            imgNews.visibility = android.view.View.VISIBLE

            Glide.with(this)
                .load(image)
                .centerCrop()
                .thumbnail(0.25f)
                .placeholder(R.drawable.news_placeholder)
                .error(R.drawable.news_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(RoundedCorners(24))
                .into(imgNews)

        } else {

            imgNews.visibility = android.view.View.GONE

        }

    }

    private fun formatDate(date: String?): String {

        if (date.isNullOrBlank()) return ""

        return try {

            val inputFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss Z",
                Locale.getDefault()
            )

            val outputFormat = SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            )

            val parsedDate = inputFormat.parse(date)

            outputFormat.format(parsedDate!!)

        } catch (e: Exception) {

            date.take(10)

        }

    }

}