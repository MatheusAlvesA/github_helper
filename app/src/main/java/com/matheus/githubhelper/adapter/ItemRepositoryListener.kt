package com.matheus.githubhelper.adapter

import com.matheus.githubhelper.models.Repository

interface ItemRepositoryListener {

    fun onClick(repository: Repository)

}