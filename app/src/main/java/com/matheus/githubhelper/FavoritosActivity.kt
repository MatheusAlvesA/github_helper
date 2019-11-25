package com.matheus.githubhelper

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheus.githubhelper.adapter.FavoritosAdapter
import com.matheus.githubhelper.adapter.ItemFavoritoListener
import com.matheus.githubhelper.dialog.RemoverDialog
import com.matheus.githubhelper.models.FavoritedRepository
import com.matheus.githubhelper.persistence.Banco
import kotlinx.android.synthetic.main.activity_favoritos.*

class FavoritosActivity : AppCompatActivity(), ItemFavoritoListener {

    private val banco = Banco(this)
    private var lista = ArrayList<FavoritedRepository>()
    private var adapter: FavoritosAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        recyclerViewFavoritos.layoutManager = LinearLayoutManager(this)

        restaurarCoresJanela()

        acessarFavoritos()
    }

    private fun acessarFavoritos() {
        lista = banco.listFavoritedRepositories()
        adapter = FavoritosAdapter(lista, this)
        recyclerViewFavoritos.adapter = adapter
    }

    override fun onLongClick(favorito: FavoritedRepository) {
        RemoverDialog.show(supportFragmentManager,
            object : RemoverDialog.OnRepositoryTrashSetListener {
                override fun onRepositoryTrashSet(boo: Boolean) {
                    if (boo == true) {
                        banco.removeFavoritedRepository(favorito.full_name)
                        val i = lista.indexOf(favorito)
                        lista.remove(favorito)
                        adapter!!.notifyItemRemoved(i)
                    }
                }
            })

    }

    private fun restaurarCoresJanela() {
        val whiteColor = ContextCompat.getColor(this, R.color.colorPrimaryWhite)
        val whiteDark = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        window.statusBarColor = whiteDark
        window.setBackgroundDrawable(ColorDrawable(whiteColor))
    }
}
