package com.matheus.githubhelper.api

import com.matheus.githubhelper.models.Commit
import com.matheus.githubhelper.models.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubAPI {
    private val service: GithubService = RetrofitInitializer().githubService()

    fun buscarRepositorios(
        query: String,
        cb: (sucesso: Boolean, resposta: ArrayList<Repository>?, erroMsg: String?) -> Unit
    ) {
        service.buscarRepositorios(query).enqueue(object: Callback<List<Repository>?> {
            override fun onResponse(call: Call<List<Repository>?>?,
                                    response: Response<List<Repository>?>?) {
                response?.body()?.let {
                    val r = ArrayList<Repository>()
                    r.addAll(it)
                    cb(true, r, null)
                }
            }
            override fun onFailure(call: Call<List<Repository>?>?,
                                   t: Throwable?) {
                if( t!= null)
                    cb(false, null, t.message)
                else
                    cb(false, null, "Falha na comunicação com o servidor")
            }
        })
    }


    fun buscarCommits(
        full_name: String,
        cb: (sucesso: Boolean, resposta: ArrayList<Commit>?, erroMsg: String?) -> Unit
    ) {
        service.buscarCommits(full_name).enqueue(object: Callback<List<Commit>?> {
            override fun onResponse(call: Call<List<Commit>?>,
                                    response: Response<List<Commit>?>) {
                if (!response.isSuccessful) {
                    cb(false, null, "Falha na consulta: "+response.message())
                    return
                }

                try {
                    response.body()!!.let {
                        val r = ArrayList<Commit>()
                        r.addAll(it)
                        cb(true, r, null)
                    }
                } catch (e: KotlinNullPointerException) {
                    cb(false, null, "Resposta inválida do servidor: "+response.raw())
                }
            }
            override fun onFailure(call: Call<List<Commit>?>?,
                                   t: Throwable?) {
                if( t!= null)
                    cb(false, null, t.message)
                else
                    cb(false, null, "Falha na comunicação com o servidor")
            }
        })
    }
}