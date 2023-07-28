package com.lytredrock.emocloudmusic.helper

/**
 * description ：  为了呈现下载记录，写了个SQLite的帮助类
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/25 13:22
 */

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lytredrock.emocloudmusic.data.DownloadSongData

class DownloadSongDataHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "download_song_data.db"
        private const val TABLE_DOWNLOAD_SONG_DATA = "download_song_data"
        private const val KEY_NAME = "name"
        private const val KEY_AUTHOR = "author"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery =
            "CREATE TABLE $TABLE_DOWNLOAD_SONG_DATA ($KEY_NAME TEXT, $KEY_AUTHOR TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOWNLOAD_SONG_DATA")
        onCreate(db)
    }

    fun clearDownloadSongData() {
        writableDatabase.delete(TABLE_DOWNLOAD_SONG_DATA, null, null)
    }

    fun addDownloadSongData(downloadSongData: DownloadSongData) {
        val values = ContentValues()
        values.put(KEY_NAME, downloadSongData.name)
        values.put(KEY_AUTHOR, downloadSongData.author)
        writableDatabase.insert(TABLE_DOWNLOAD_SONG_DATA, null, values)
    }

    @SuppressLint("Range")
    fun getAllDownloadSongData(): List<DownloadSongData> {
        val downloadSongDataList = mutableListOf<DownloadSongData>()
        val selectQuery = "SELECT * FROM $TABLE_DOWNLOAD_SONG_DATA"
        val cursor: Cursor? = readableDatabase.rawQuery(selectQuery, null)
        cursor?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndex(KEY_NAME))
                val author = getString(getColumnIndex(KEY_AUTHOR))
                val downloadSongData = DownloadSongData(name, author)
                downloadSongDataList.add(downloadSongData)
            }
        }
        cursor?.close()
        return downloadSongDataList
    }
}