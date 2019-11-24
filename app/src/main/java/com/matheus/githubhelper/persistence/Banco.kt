package com.matheus.githubhelper.persistence

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.matheus.githubhelper.models.FavoritedRepository

class Banco(ctx: Context) {
    private val helper = GithubSqlHelper(ctx)

    fun insertFavoriteRepository(newRep: FavoritedRepository) {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_NAME, newRep.full_name)
            put(COLUMN_COMMIT, newRep.commit_id)
        }
        db.insert(TABLE_REPOSITORY_NAME, null, cv)
        db.close()

        //Toast.makeText(this, "repositorio favoritado com sucesso", Toast.LENGTH_LONG).show()
    }

    fun listFavoritedRepositories(): ArrayList<FavoritedRepository> {
        val sql = "SELECT * FROM $TABLE_REPOSITORY_NAME"
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, arrayOf())

        val lista = ArrayList<FavoritedRepository>()
        while(cursor.moveToNext())
            lista.add(FavoritedRepository(
                                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_COMMIT))
                        ))

        cursor.close()
        return lista
    }

    fun updateFavoritedRepository(rep: FavoritedRepository) {
        removeFavoritedRepository(rep.full_name)
        insertFavoriteRepository(rep)
    }

    fun removeFavoritedRepository(full_name: String) {
        val db = helper.writableDatabase
        db.delete(
            TABLE_REPOSITORY_NAME,
            "$COLUMN_NAME = ? ",
            arrayOf(full_name)
        )
        db.close()
    }

    fun listLastSearchs(): ArrayList<String> {
        val sql = "SELECT * FROM $TABLE_CACHE_NAME"
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, arrayOf())

        val lista = ArrayList<String>()
        while(cursor.moveToNext())
            lista.add(cursor.getString(cursor.getColumnIndex(COLUMN_SEARCH)))

        cursor.close()
        return lista
    }

    fun clearLastSearchs() {
        val sql = "SELECT * FROM $TABLE_CACHE_NAME"
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, arrayOf())

        val lista = ArrayList<Int>()
        while(cursor.moveToNext())
            lista.add(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)))
        cursor.close()

        if(lista.size > 5) {
            var i = 5
            while(i < lista.size) {
                removeSearch(lista[i])
                i++
            }
        }

    }

    fun insertSearch(search: String) {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_SEARCH, search)
        }
        db.insert(TABLE_CACHE_NAME, null, cv)
        db.close()
        clearLastSearchs()
    }

    fun removeSearch(id: Int) {
        val db = helper.writableDatabase
        db.delete(
            TABLE_CACHE_NAME,
            "$COLUMN_ID = ? ",
            arrayOf(id.toString())
        )
        db.close()
    }

}