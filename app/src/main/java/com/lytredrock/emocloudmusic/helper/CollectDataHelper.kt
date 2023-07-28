package com.lytredrock.emocloudmusic.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lytredrock.emocloudmusic.data.Collect

/**
 * description ： 为了实现收藏，写了个SQLite的帮助类
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/25 21:24
 */
class CollectDataHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "collect_database.db"
        private const val TABLE_NAME = "collect"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_AUTHOR = "author"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT, $KEY_AUTHOR TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addCollect(collect: Collect) {
        val values = ContentValues()
        values.put(KEY_NAME, collect.name)
        values.put(KEY_AUTHOR, collect.author)
        values.put(KEY_ID, collect.id)
        writableDatabase.insert(TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun getAllCollects(): List<Collect> {
        val collectList = mutableListOf<Collect>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor? = readableDatabase.rawQuery(selectQuery, null)
        cursor?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndex(KEY_NAME))
                val author = getString(getColumnIndex(KEY_AUTHOR))
                val id = getInt(getColumnIndex(KEY_ID))
                val collect = Collect(name, author, id)
                collectList.add(collect)
            }
        }
        cursor?.close()
        return collectList
    }

    fun clearDatabase() {
        writableDatabase.delete(TABLE_NAME, null, null)
    }

    fun isCollectExists(collect: Collect): Boolean {
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $KEY_ID = ?"
        val cursor: Cursor? = readableDatabase.rawQuery(selectQuery, arrayOf(collect.id.toString()))
        val exists = cursor?.count ?: 0 > 0
        cursor?.close()
        return exists
    }

    fun removeCollect(collect: Collect) {
        val whereClause = "$KEY_ID = ?"
        val whereArgs = arrayOf(collect.id.toString())
        writableDatabase.delete(TABLE_NAME, whereClause, whereArgs)
    }
}