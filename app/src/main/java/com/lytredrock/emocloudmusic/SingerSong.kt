package com.lytredrock.emocloudmusic

import BaseActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.lytredrock.emocloudmusic.adapter.SingerSongAdapter
import com.lytredrock.emocloudmusic.databinding.ActivitySingerSongBinding
import com.lytredrock.emocloudmusic.viewmodel.SingerSongViewModel

class SingerSong : BaseActivity() {

    private val myViewBinding: ActivitySingerSongBinding by lazy {
        ActivitySingerSongBinding.inflate(
            layoutInflater
        )
    }
    private val myViewModel by lazy { ViewModelProvider(this)[SingerSongViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myViewBinding.root)
        setSupportActionBar(myViewBinding.toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        transparentStatusBar(window, false)

        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")
        val photo = intent.getStringExtra("photo")

        myViewBinding.collapsingToolbar.title = name

        Glide.with(this).load(photo).into(myViewBinding.ivSinger)
        myViewModel.apply {
            getSingerSongInInternet(id)
            singerSongLifeData.observe(this@SingerSong) {
                myViewBinding.rvSingerSong.apply {
                    adapter = SingerSongAdapter(it, this@SingerSong)
                    layoutManager = GridLayoutManager(this@SingerSong, 1)
                }

            }

        }
    }
}