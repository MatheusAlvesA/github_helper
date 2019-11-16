package com.matheus.githubhelper.adapter

import com.matheus.githubhelper.models.FavoritedRepository

interface ItemFavoritoListener {

    fun onLongClick(favorito: FavoritedRepository)

}