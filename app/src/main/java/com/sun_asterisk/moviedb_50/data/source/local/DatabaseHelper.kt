package com.sun_asterisk.moviedb_50.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sun_asterisk.moviedb_50.data.model.Favorite
import com.sun_asterisk.moviedb_50.utils.Constant

class DatabaseHelper private constructor(val context: Context) : SQLiteOpenHelper(
    context,
    Constant.BASE_DATABASE_NAME,
    null,
    Constant.BASE_DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Favorite.MovieEntry.SQL_FAVORITE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        context.deleteDatabase(Constant.BASE_DATABASE_NAME);
    }

    companion object {
        private var instance: DatabaseHelper? = null
        fun getInstance(context: Context): DatabaseHelper =
            instance ?: DatabaseHelper(context).also { instance = it }
    }
}
