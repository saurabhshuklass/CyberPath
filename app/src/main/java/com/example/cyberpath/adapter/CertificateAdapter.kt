package com.example.cyberpath.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberpath.R
import com.example.cyberpath.model.CertificateHistory

class CertificateAdapter(
    private val historyList: List<CertificateHistory>
) : RecyclerView.Adapter<CertificateAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtCourseName: TextView = itemView.findViewById(R.id.txtCourseName)
        val txtScore: TextView = itemView.findViewById(R.id.txtScore)
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val imgCertificate: ImageView = itemView.findViewById(R.id.imgCertificate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_certificate, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = historyList[position]

        holder.txtCourseName.text = item.courseName
        holder.txtScore.text = "Score : ${item.bestQuizScore}%"
        holder.txtDate.text = item.issueDate

    }

    override fun getItemCount(): Int = historyList.size
}