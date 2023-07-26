package com.lytredrock.model.research.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.lytredrock.lib.base.BaseUtils.transparentStatusBar
import com.lytredrock.model.research.adapter.RvArtistSongAdapter
import com.lytredrock.model.research.databinding.ActivityArtistShowBinding
import com.lytredrock.model.research.searchviewmodel.ArtistMusicViewModel

@Route(path = "/artist/music")
class ArtistShowActivity : AppCompatActivity() {

    @Autowired
    lateinit var id: String

    @Autowired
    lateinit var picUrl: String
    private val mBinding: ActivityArtistShowBinding by lazy {
        ActivityArtistShowBinding.inflate(
            layoutInflater
        )
    }
    private val myViewModel by lazy { ViewModelProvider(this)[ArtistMusicViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        ARouter.getInstance().inject(this@ArtistShowActivity)
        transparentStatusBar(window, false)
        iniUpData()

    }

    private fun iniUpData() {
        if (id.isNotEmpty() && picUrl.isNotEmpty()) {
            myViewModel.getArtistMusic(id)
            myViewModel.artistMusicData.observe(this) {
                Glide.with(this).load(picUrl).into(mBinding.ivSongList)
                mBinding.rvSongList.apply {
                    adapter = RvArtistSongAdapter(it, this@ArtistShowActivity)
                    layoutManager = GridLayoutManager(this@ArtistShowActivity, 1)
                }
            }
        }
    }
}