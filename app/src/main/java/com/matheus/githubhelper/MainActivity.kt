package com.matheus.githubhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.matheus.githubhelper.api.GithubAPI

class MainActivity : AppCompatActivity() {

    private val api: GithubAPI = GithubAPI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api.buscarRepositorios("android") { sucesso, res, erro ->
            if(sucesso) {
                Toast.makeText(this, "Sucesso: "+res?.size, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Erro: $erro", Toast.LENGTH_LONG).show()
            }
        }
    }
}
