package com.matheus.githubhelper.api

import com.matheus.githubhelper.models.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    fun buscarRepositorios(@Query("q") query: String): Call<List<Repository>>
}