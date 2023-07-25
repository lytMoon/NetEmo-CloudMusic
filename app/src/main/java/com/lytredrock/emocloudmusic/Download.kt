package com.lytredrock.emocloudmusic

import BaseActivity
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lytredrock.emocloudmusic.adapter.DownloadAdapter
import com.lytredrock.emocloudmusic.data.CollectData
import com.lytredrock.emocloudmusic.databinding.ActivityDownloadBinding
import com.lytredrock.emocloudmusic.helper.DownloadDatabaseHelper
import com.lytredrock.emocloudmusic.viewmodel.DownloadViewModel

class Download : BaseActivity() {

    private val myViewBinding: ActivityDownloadBinding by lazy {
        ActivityDownloadBinding.inflate(
            layoutInflater
        )
    }

    private val myViewModel by lazy { ViewModelProvider(this)[DownloadViewModel::class.java] }

    val downloadedSongs = mutableListOf<CollectData>()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myViewBinding.root)
        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")
        val mv = intent.getIntExtra("mv", 0)
        val author = intent.getStringExtra("author")
        val downloadedSong = CollectData(name, author, mv)
        myViewModel.apply {
            getDownloadDataInInternet(id, "exhigh")
            downloadDataLifeData.observe(this@Download) {
                startDownload(it[0].url, "${name}.mp3")
            }
        }

        val dbHelper = DownloadDatabaseHelper(this)
        dbHelper.addCollectData(downloadedSong)
        downloadedSongs.addAll(dbHelper.getAllCollectData())
        Log.d("GGG", "onCreate: $downloadedSongs")
         if(id!=0){
             myViewBinding.rvDownload.apply {
                 adapter = DownloadAdapter(downloadedSongs,this@Download)
                 layoutManager = LinearLayoutManager(this@Download)
             }
         }
    }

    @SuppressLint("Range")
    private fun startDownload(downloadUrl: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(downloadUrl))
        request.setTitle("Downloading MP3") // 设置下载通知栏的标题
        request.setDescription("Downloading $fileName") // 设置下载通知栏的描述信息
        request.setAllowedOverMetered(true) // 允许计费网络下载
        request.setMimeType("audio/mpeg") // 设置下载的文件类型为 MP3
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // 在下载过程中和下载完成后都显示通知栏

        // 设置文件保存路径和文件名
        val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(downloadUrl)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        // 获取下载管理器的实例
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)


    }
}


