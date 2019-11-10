package com.matheus.githubhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheus.githubhelper.adapter.GithubAdapter
import com.matheus.githubhelper.adapter.ItemRepositoryListener
import com.matheus.githubhelper.api.GithubAPI
import com.matheus.githubhelper.models.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemRepositoryListener {

    private val api: GithubAPI = GithubAPI()
    //private var list : ArrayList<Repository> = ArrayList()
    //private var adapter: GithubAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //adapter = GithubAdapter(list)
        //recyclerView.adapter = adapter

        btnBuscar.setOnClickListener {
            acessarRepositorios(edtBuscar.text.toString())
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun acessarRepositorios(chave: String){
        api.buscarRepositorios(chave) { sucesso, res, erro ->
            if(sucesso) {
                val adapter = GithubAdapter(res!!, this)
                recyclerView.adapter = adapter
                Toast.makeText(this, "Sucesso: "+res?.size, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Erro: $erro", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(repository: Repository) {
        val intent = Intent(this, RepositoryActivity::class.java)
        val bundle = Bundle()

        bundle.putInt("id", repository.id)
        bundle.putString("name", repository.name)
        bundle.putString("full_name", repository.full_name)
        bundle.putString("html_url", repository.html_url)
        bundle.putString("created_at", repository.created_at)
        bundle.putString("updated_at", repository.updated_at)
        bundle.putString("clone_url", repository.clone_url)
        bundle.putBoolean("private", repository.private)
        bundle.putInt("stargazers_count", repository.stargazers_count)
        bundle.putInt("forks_count", repository.forks_count)
        bundle.putInt("open_issues_count", repository.open_issues_count)
        intent.putExtra("repository", bundle)

        startActivity(intent)
    }
}
