package com.lytredrock.emocloudmusic

import BaseActivity
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lytredrock.emocloudmusic.adapter.DownloadAdapter
import com.lytredrock.emocloudmusic.data.DownloadSongData
import com.lytredrock.emocloudmusic.databinding.ActivityDownloadBinding
import com.lytredrock.emocloudmusic.helper.DownloadSongDataHelper
import com.lytredrock.emocloudmusic.viewmodel.DownloadViewModel

class Download : BaseActivity() {

    private val myViewBinding: ActivityDownloadBinding by lazy {
        ActivityDownloadBinding.inflate(
            layoutInflater
        )
    }

    private val myViewModel by lazy { ViewModelProvider(this)[DownloadViewModel::class.java] }

    val downloadedSongs = mutableListOf<DownloadSongData>()
    val clearSongs = mutableListOf<DownloadSongData>()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myViewBinding.root)
        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")
        val author = intent.getStringExtra("author")
        val downloadedSong = DownloadSongData(name, author)
        myViewModel.apply {
            if (id != 0) {
                getDownloadDataInInternet(id, "exhigh")
                downloadDataLifeData.observe(this@Download) {
                    startDownload(it[0].url, "${name}.mp3")
                }
            }
        }

        val helper = DownloadSongDataHelper(this)
        if (id != 0) {
            helper.addDownloadSongData(downloadedSong)
        }
        downloadedSongs.addAll(helper.getAllDownloadSongData())

        myViewBinding.rvDownload.apply {
            adapter = DownloadAdapter(downloadedSongs)
            layoutManager = LinearLayoutManager(this@Download)
        }


        myViewBinding.ivClearDownload.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("下载记录")
                setMessage("是否清空下载记录")
                setCancelable(false)
                setPositiveButton("是") { dialog, _ ->
                    helper.clearDownloadSongData()
                    myViewBinding.rvDownload.apply {
                        adapter = DownloadAdapter(clearSongs)
                        layoutManager = LinearLayoutManager(this@Download)
                    }
                    dialog.dismiss()
                }
                setNegativeButton("否") { dialog, _ ->
                    dialog.dismiss()

                }
            }.show()
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
        val downloadDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(downloadUrl)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        // 获取下载管理器的实例
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)
        Thread {
            var downloading = true
            val query = DownloadManager.Query()
            query.setFilterById(downloadId)
            while (downloading) {
                val cursor = downloadManager.query(query)
                if (cursor?.moveToFirst() == true) {
                    val downloaded =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val total =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    val progress = (downloaded.toFloat() / total.toFloat() * 100).toInt()

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        // 下载完成
                        downloading = false
                        // 执行下载完成后的操作
                        runOnUiThread {
                            Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                cursor?.close()
                Thread.sleep(100) // 0.1秒钟查询一次下载进度
            }
        }.start()

    }
}


