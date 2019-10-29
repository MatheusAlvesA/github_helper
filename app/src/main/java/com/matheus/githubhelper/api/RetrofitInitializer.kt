package com.matheus.githubhelper.api

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class RetrofitInitializer {
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(List::class.java, Deserializer())
        .create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun githubService(): GithubService {
        return retrofit.create(GithubService::class.java)
    }

}