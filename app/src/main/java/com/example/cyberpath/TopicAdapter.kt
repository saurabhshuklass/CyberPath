package com.example.cyberpath.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberpath.R
import com.example.cyberpath.TopicDetailActivity
import com.example.cyberpath.models.TopicModel

class TopicAdapter(
    private val topicList: ArrayList<TopicModel>
) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    class TopicViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imgIcon: ImageView =
            itemView.findViewById(R.id.imgIcon)

        val txtTitle: TextView =
            itemView.findViewById(R.id.txtTitle)

        val txtStatus: TextView =
            itemView.findViewById(R.id.txtStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_topic,
                parent,
                false
            )

        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TopicViewHolder,
        position: Int
    ) {

        val topic = topicList[position]

        holder.imgIcon.setImageResource(topic.icon)

        holder.txtTitle.text = topic.title

        if (topic.isCompleted) {

            holder.txtStatus.text = "✅ Completed"

        } else {

            holder.txtStatus.text = "🔓 Open"
        }

        holder.itemView.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                TopicDetailActivity::class.java
            )

            intent.putExtra(
                "topicTitle",
                topic.title
            )

            holder.itemView.context
                .startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return topicList.size
    }
}