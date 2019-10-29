package com.matheus.githubhelper.api

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.matheus.githubhelper.models.Repository
import java.lang.reflect.Type

class Deserializer: JsonDeserializer<List<Repository>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Repository> {
        val lista = json!!.asJsonObject.get("items").asJsonArray
        val res = ArrayList<Repository>()
        for(r in lista) {
            res.add(Gson().fromJson(r, Repository::class.java))
        }
        return res
    }

}