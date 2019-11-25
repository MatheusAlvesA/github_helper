package com.matheus.githubhelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.matheus.githubhelper.R
import com.matheus.githubhelper.models.Commit

class RepositoryAdapter(
    private val commits: List<Commit>
): RecyclerView.Adapter<RepositoryAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.commit_item, parent, false)

        return VH(v)
    }

    override fun getItemCount(): Int = commits.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val commit = commits.get(position)
        holder.authorName.text = commit.name_author
        holder.authorEmail.text = commit.email_author
        holder.message.text = commit.message
    }


    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var authorName: TextView = itemView.findViewById(R.id.txtNameAuthor)
        var authorEmail: TextView = itemView.findViewById(R.id.txtEmailAuthor)
        var message: TextView = itemView.findViewById(R.id.txtMessage)
    }
}