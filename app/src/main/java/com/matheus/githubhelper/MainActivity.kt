package com.matheus.githubhelper

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheus.githubhelper.adapter.GithubAdapter
import com.matheus.githubhelper.adapter.ItemRepositoryListener
import com.matheus.githubhelper.api.GithubAPI
import com.matheus.githubhelper.models.Repository
import com.matheus.githubhelper.services.SyncJobService
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

        restaurarCoresJanela()
        setupService()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favoritos_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title.toString() == "favoritos") {
            startActivity(Intent(this, FavoritosActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun acessarRepositorios(chave: String) {
        api.buscarRepositorios(chave) { sucesso, res, erro ->
            if(sucesso) {
                val adapter = GithubAdapter(res!!, this)
                recyclerView.adapter = adapter
                Toast.makeText(this, "Sucesso: "+res.size, Toast.LENGTH_LONG).show()
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

    private fun setupService() {
       val service =  getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
       val component = ComponentName(this, SyncJobService::class.java)
       val jobInfo = JobInfo.Builder(1, component)
                    .setPeriodic(900000)
                    .build()

      service.schedule(jobInfo)
    }

    private fun restaurarCoresJanela() {
        val whiteColor = ContextCompat.getColor(this, R.color.colorPrimaryWhite)
        val whiteDark = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        window.statusBarColor = whiteDark
        window.setBackgroundDrawable(ColorDrawable(whiteColor))
    }
}
