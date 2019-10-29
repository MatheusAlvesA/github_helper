package com.matheus.githubhelper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.matheus.githubhelper.R
import com.matheus.githubhelper.models.Repository

class GithubAdapter(private val repositorios: List<Repository>): RecyclerView.Adapter<GithubAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false)

        return VH(v)
    }

    override fun getItemCount(): Int = repositorios.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var repository = repositorios.get(position)

        holder.name.text = repository.name
        holder.fullName.text = repository.full_name
    }


    class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.txtName)
        var fullName: TextView = itemView.findViewById(R.id.txtFullName)
    }
}