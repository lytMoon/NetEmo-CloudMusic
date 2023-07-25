package com.lytredrock.emocloudmusic.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lytredrock.emocloudmusic.data.Collect
import com.lytredrock.emocloudmusic.data.CollectData

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/25 15:32
 */
class CollectDataHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "collect_data.db"
        private const val TABLE_COLLECT_DATA = "collect_data"
        private const val KEY_NAME = "name"
        private const val KEY_AUTHOR = "author"
        private const val KEY_MV = "mv"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_COLLECT_DATA ($KEY_NAME TEXT, $KEY_AUTHOR TEXT, $KEY_MV INTEGER)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COLLECT_DATA")
        onCreate(db)
    }

    fun addCollectData(collectData: Collect) {
        val values = ContentValues()
        values.put(KEY_NAME, collectData.name)
        values.put(KEY_AUTHOR, collectData.author)
        values.put(KEY_MV, collectData.mv)
        writableDatabase.insert(TABLE_COLLECT_DATA, null, values)
    }

    @SuppressLint("Range")
    fun getAllCollectData(): List<Collect> {
        val collectDataList = mutableListOf<Collect>()
        val selectQuery = "SELECT * FROM $TABLE_COLLECT_DATA"
        val cursor: Cursor? = readableDatabase.rawQuery(selectQuery, null)
        cursor?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndex(KEY_NAME))
                val author = getString(getColumnIndex(KEY_AUTHOR))
                val mv = getInt(getColumnIndex(KEY_MV))
                val collectData = Collect(name, author, mv)
                collectDataList.add(collectData)
            }
        }
        cursor?.close()
        return collectDataList
    }
}