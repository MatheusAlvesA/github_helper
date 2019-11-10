package com.matheus.githubhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.matheus.githubhelper.api.GithubAPI
import com.matheus.githubhelper.models.Repository
import kotlinx.android.synthetic.main.activity_repository.*

class RepositoryActivity : AppCompatActivity() {

    private lateinit var repository: Repository
    private val api: GithubAPI = GithubAPI()

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
}
