package com.matheus.githubhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheus.githubhelper.adapter.GithubAdapter
import com.matheus.githubhelper.api.GithubAPI
import com.matheus.githubhelper.models.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    private val api: GithubAPI = GithubAPI()
    //private var list : ArrayList<Repository> = ArrayList()
    //private var adapter: GithubAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //adapter = GithubAdapter(list)
        //recyclerView.adapter = adapter

        btnBuscar.setOnClickListener(){
            acessarRepositorios(edtBuscar.text.toString())
        }
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    fun acessarRepositorios(chave: String){
        api.buscarRepositorios(chave) { sucesso, res, erro ->
            if(sucesso) {
                val adapter = GithubAdapter(res!!)
                recyclerView.adapter = adapter
                Toast.makeText(this, "Sucesso: "+res?.size, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Erro: $erro", Toast.LENGTH_LONG).show()
            }
        }
    }
}
