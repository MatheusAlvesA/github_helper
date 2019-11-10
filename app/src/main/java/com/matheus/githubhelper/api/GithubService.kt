package com.matheus.githubhelper.api

import com.matheus.githubhelper.models.Commit
import com.matheus.githubhelper.models.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    fun buscarRepositorios(@Query("q") query: String): Call<List<Repository>>

    @GET("repos/{full_name}/commits")
    fun buscarCommits(@Path(value = "full_name", encoded = true) full_name: String): Call<List<Commit>>
}