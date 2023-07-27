package com.lytredrock.emocloudmusic

import BaseActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.lytredrock.emocloudmusic.adapter.SongListAdapter
import com.lytredrock.emocloudmusic.databinding.ActivitySongListBinding
import com.lytredrock.emocloudmusic.viewmodel.SongListViewModel

class SongListActivity : BaseActivity() {
    private val myViewBinding: ActivitySongListBinding by lazy {
        ActivitySongListBinding.inflate(
            layoutInflater
        )
    }
    private val myViewModel by lazy { ViewModelProvider(this)[SongListViewModel::class.java] }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myViewBinding.root)
        setSupportActionBar(myViewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        transparentStatusBar(window, false)

        val id = intent.getLongExtra("id", 0)
        val name = intent.getStringExtra("name")
        val photo = intent.getStringExtra("photo")


        myViewBinding.collapsingToolbar.title = name
        Glide.with(this@SongListActivity).load(photo).into(myViewBinding.ivSongList)

        myViewModel.apply {
            getSongListInInternet(id)
            songLifeData.observe(this@SongListActivity) {
                myViewBinding.rvSongList.apply {
                    adapter = SongListAdapter(it, this@SongListActivity)
                    layoutManager = GridLayoutManager(this@SongListActivity, 1)
                }

            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}



