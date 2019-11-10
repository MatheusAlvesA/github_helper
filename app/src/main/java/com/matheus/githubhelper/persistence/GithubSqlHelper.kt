package com.matheus.githubhelper.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "db_github_helper"
const val DATABASE_VERSION = 1
const val TABLE_REPOSITORY_NAME = "favorited_repositories"
const val TABLE_CACHE_NAME = "search_cache"
const val COLUMN_ID = "_id"
const val COLUMN_NAME = "full_name"
const val COLUMN_SEARCH = "search"
const val COLUMN_COMMIT = "last_commit_id"

class GithubSqlHelper(ctx: Context): SQLiteOpenHelper(
    ctx,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE $TABLE_REPOSITORY_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT NOT NULL, " +
                "$COLUMN_COMMIT TEXT NOT NULL)")

        db.execSQL("CREATE TABLE $TABLE_CACHE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_SEARCH TEXT NOT NULL)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}