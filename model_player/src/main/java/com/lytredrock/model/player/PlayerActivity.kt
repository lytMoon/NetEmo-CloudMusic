package com.lytredrock.model.player

import BaseActivity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.lytredrock.model.player.databinding.ActivityPlayerBinding
import com.lytredrock.model.player.viewmodel.PlayerViewModel


class PlayerActivity : BaseActivity() {

    //懒加载注入viewModel
    private val playerViewModel by lazy {
        ViewModelProvider(this)[PlayerViewModel::class.java]
    }
    //懒加载注入viewBinding
    private val mBinding: ActivityPlayerBinding by lazy { ActivityPlayerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        transparentStatusBar(window,false)
        iniBind()
        playerViewModel.getMusicUrl("2059741543")
        playMusic()
    }

    private fun iniBind() {



    }


    /**
     * 播放音乐
     */
    private fun playMusic() {
        val mPlayer = ExoPlayer.Builder(this).build()
        mBinding.exoPlayer.player=mPlayer
        playerViewModel.musicInfo.observe(this){
            val url= it[0].url
            Log.d("playMusic","(PlayerActivity.kt:47)-->> ${it[0].url}");
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            mPlayer.setMediaItem(mediaItem)//准备媒体资源
            mPlayer.prepare()
            mPlayer.play()//开始播放
        }





    }


}