package com.example.cyberpath.intro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberpath.R

class IntroAdapter(
    private val introList: List<IntroModel>
) : RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {

    class IntroViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.imgIntro)
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val subtitle: TextView = itemView.findViewById(R.id.txtSubtitle)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntroViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_intro,
                parent,
                false
            )

        return IntroViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: IntroViewHolder,
        position: Int
    ) {

        val item = introList[position]

        holder.image.setImageResource(item.image)
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle
    }

    override fun getItemCount(): Int {
        return introList.size
    }
}