package com.lytredrock.emocloudmusic

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lytredrock.emocloudmusic.adapter.CollectAdapter
import com.lytredrock.emocloudmusic.adapter.DownloadAdapter
import com.lytredrock.emocloudmusic.data.Collect
import com.lytredrock.emocloudmusic.data.DownloadSongData
import com.lytredrock.emocloudmusic.databinding.ActivityCollectBinding
import com.lytredrock.emocloudmusic.helper.CollectDataHelper

class Collect : AppCompatActivity() {

    private val myViewBinding: ActivityCollectBinding by lazy {
        ActivityCollectBinding.inflate(
            layoutInflater
        )
    }
    val collectedSongs = mutableListOf<Collect>()
    val clearSongs=mutableListOf<DownloadSongData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myViewBinding.root)
        val name = intent.getStringExtra("name")
        val author = intent.getStringExtra("author")
        val id=intent.getIntExtra("id",0)
        val collectSong= Collect(name,author,id)




        val helper = CollectDataHelper(this)
        if(!helper.isCollectExists(collectSong)&&id!=0) {
            helper.addCollect(collectSong)
        }
        collectedSongs.addAll(helper.getAllCollects())
        myViewBinding.rvCollect.apply {
            adapter = CollectAdapter(collectedSongs,helper,this@Collect)
            layoutManager = LinearLayoutManager(this@Collect)
        }


        myViewBinding.ivClearCollect.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("收藏歌单")
                setMessage("是否清空收藏歌单")
                setCancelable(false)
                setPositiveButton("是"){
                        dialog, _ ->
                    helper.clearDatabase()
                    myViewBinding.rvCollect.apply {
                        adapter = DownloadAdapter(clearSongs)
                        layoutManager = LinearLayoutManager(this@Collect)
                    }
                    dialog.dismiss()
                }
                setNegativeButton("否"){
                        dialog, _ ->
                    dialog.dismiss()
                }
            }.show()
        }

    }
}