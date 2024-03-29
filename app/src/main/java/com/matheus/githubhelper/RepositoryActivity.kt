package com.matheus.githubhelper

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheus.githubhelper.adapter.RepositoryAdapter
import com.matheus.githubhelper.api.GithubAPI
import com.matheus.githubhelper.models.FavoritedRepository
import com.matheus.githubhelper.models.Repository
import com.matheus.githubhelper.persistence.Banco
import kotlinx.android.synthetic.main.activity_repository.*

class RepositoryActivity : AppCompatActivity() {

    private lateinit var repository: Repository
    private val api: GithubAPI = GithubAPI()
    private val banco = Banco(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        repository = getRepositoryFromBundle(intent.getBundleExtra("repository")!!)
        rvComit.layoutManager = LinearLayoutManager(this)

        api.buscarCommits(repository.full_name) {sucesso, res, erro ->
            if(sucesso) {
                val adapter = RepositoryAdapter(res!!)
                rvComit.adapter = adapter
                Toast.makeText(this, "Commits: "+res.size, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
            }
        }

        nameRepository.text = repository.name

        restaurarCoresJanela()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title.toString() == "acessar") {
            val abrirNavegador = Intent(Intent.ACTION_VIEW)
            abrirNavegador.data = Uri.parse(repository.html_url)
            startActivity(abrirNavegador)
        } else if (item.title.toString() == "favoritar"){
            if(favoritado()) {
                banco.removeFavoritedRepository(repository.full_name)
                item.setIcon(android.R.drawable.star_big_off)
                Toast.makeText(this, "repositorio removido com sucesso", Toast.LENGTH_LONG).show()
            } else {
                banco.insertFavoriteRepository(
                    FavoritedRepository(repository.full_name, "")
                )
                item.setIcon(android.R.drawable.star_big_on)
                Toast.makeText(this, "repositorio favoritado com sucesso", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun favoritado(): Boolean {
        val lista = banco.listFavoritedRepositories()
        for(rep in lista) {
            if(rep.full_name == repository.full_name)
                return true
        }
        return false
    }

    private fun getRepositoryFromBundle(bundle: Bundle): Repository {
        return Repository(
            bundle.getInt("id"),
            bundle.getString("name")!!,
            bundle.getString("full_name")!!,
            bundle.getBoolean("private"),
            bundle.getString("html_url")!!,
            bundle.getString("created_at")!!,
            bundle.getString("updated_at")!!,
            bundle.getString("clone_url")!!,
            bundle.getInt("stargazers_count"),
            bundle.getInt("forks_count"),
            bundle.getInt("open_issues_count")
        )
    }

    @SuppressLint("NewApi")
    private fun restaurarCoresJanela() {
        val whiteColor = ContextCompat.getColor(this, R.color.colorPrimaryWhite)
        val whiteDark = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        window.statusBarColor = whiteDark
        window.setBackgroundDrawable(ColorDrawable(whiteColor))
    }
}
