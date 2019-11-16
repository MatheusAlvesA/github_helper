package com.matheus.githubhelper

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
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

        api.buscarCommits(repository.full_name) {sucesso, res, erro ->
            if(sucesso) {
                Toast.makeText(this, "Commits: "+res?.size, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
            }
        }

        nameRepository.text = repository.name
        atualiarImagem()
        starImage.setOnClickListener {
            favoritar()
        }

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
        }
        return super.onOptionsItemSelected(item)
    }

    fun favoritar() {
        if(favoritado()) {
            banco.removeFavoritedRepository(repository.full_name)
        } else {
            banco.insertFavoriteRepository(
                FavoritedRepository(repository.full_name, "")
            )
        }
        atualiarImagem()
    }

    private fun favoritado(): Boolean {
        val lista = banco.listFavoritedRepositories()
        for(rep in lista) {
            if(rep.full_name == repository.full_name)
                return true
        }
        return false
    }

    private fun atualiarImagem() {
        if(favoritado()) {
            starImage.setImageResource(android.R.drawable.star_big_on)
        } else {
            starImage.setImageResource(android.R.drawable.star_big_off)
        }
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

    private fun restaurarCoresJanela() {
        val whiteColor = ContextCompat.getColor(this, R.color.colorPrimaryWhite)
        val whiteDark = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        window.statusBarColor = whiteDark
        window.setBackgroundDrawable(ColorDrawable(whiteColor))
    }
}
