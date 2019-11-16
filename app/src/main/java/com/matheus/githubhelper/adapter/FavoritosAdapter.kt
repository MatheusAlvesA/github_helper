package com.matheus.githubhelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.matheus.githubhelper.R
import com.matheus.githubhelper.models.FavoritedRepository

class FavoritosAdapter(
    private val repositorios: List<FavoritedRepository>,
    private val listener: ItemFavoritoListener
): RecyclerView.Adapter<FavoritosAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false)

        return VH(v)
    }

    override fun getItemCount(): Int = repositorios.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val rep = repositorios.get(position)
        holder.bindView(rep, listener)
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.txtName)
        var fullName: TextView = itemView.findViewById(R.id.txtFullName)
        val v = itemView as MaterialCardView

        fun bindView(repository: FavoritedRepository, listener: ItemFavoritoListener) {
            this.name.text = repository.full_name
            this.fullName.text = "Segure para excluir"

            v.setOnLongClickListener {
                listener.onLongClick(repository)
                true
            }
        }
    }
}