package com.matheus.githubhelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.matheus.githubhelper.R
import com.matheus.githubhelper.models.Repository

class GithubAdapter(
        private val repositorios: List<Repository>,
        private val listener: ItemRepositoryListener
    ): RecyclerView.Adapter<GithubAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false)

        return VH(v)
    }

    override fun getItemCount(): Int = repositorios.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val repository = repositorios.get(position)
        holder.bindView(repository, listener)
    }


    class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.txtName)
        var fullName: TextView = itemView.findViewById(R.id.txtFullName)
        val v = itemView as MaterialCardView

        fun bindView(repository: Repository, listener: ItemRepositoryListener) {
            this.name.text = repository.name
            this.fullName.text = repository.full_name

            v.setOnClickListener { listener.onClick(repository) }
        }
    }
}