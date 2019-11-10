package com.matheus.githubhelper.api

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.matheus.githubhelper.models.Commit
import com.matheus.githubhelper.models.Repository
import java.lang.RuntimeException
import java.lang.reflect.Type

class Deserializer: JsonDeserializer<List<*>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<*> {

        if(typeOfT.toString() == "java.util.List<"+Repository::class.java.name+">") {
            val lista = json!!.asJsonObject.get("items").asJsonArray
            val res = ArrayList<Repository>()
            for (r in lista) {
                res.add(Gson().fromJson(r, Repository::class.java))
            }
            return res
        }
        if(typeOfT.toString() == "java.util.List<"+Commit::class.java.name+">") {
            val lista = json!!.asJsonArray
            val res = ArrayList<Commit>()
            for (r in lista) {
                val parsed = r.asJsonObject
                val id: String = parsed.get("sha").asString
                val nameAuthor: String = parsed.get("commit")
                                            .asJsonObject.get("author")
                                            .asJsonObject.get("name")
                                            .asString
                val emailAuthor: String = parsed.get("commit")
                    .asJsonObject.get("author")
                    .asJsonObject.get("email")
                    .asString
                val msg: String = parsed.get("commit").asJsonObject.get("message").asString

                res.add(Commit(id, nameAuthor, emailAuthor, msg))
            }
            return res
        }

        throw RuntimeException("Impossível desserializar a classe "+typeOfT.toString())
    }

}