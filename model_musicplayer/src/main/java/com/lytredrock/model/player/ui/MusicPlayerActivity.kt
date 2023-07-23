package com.lytredrock.model.player.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lytredrock.lib.base.BaseUtils.transparentStatusBar
import com.lytredrock.model.player.R
import com.lytredrock.model.player.databinding.ActivityPlayerBinding
import com.lytredrock.model.player.fragment.PageOneFragment
import com.lytredrock.model.player.fragment.PageTwoFragment
import com.lytredrock.model.player.viewmodel.MusicPlayerViewModel


class MusicPlayerActivity : AppCompatActivity() {


    /**
     * 本来准备设置标记符，但是当你切换到第二个的时候，点击事件被vp拦截了，再次点击的时候不会切换回来（除非vp为空，所以放弃了这种判断方法）
     */
//    //设置要切换fragment的标记符
//    private var isFragmentOneDisplayed = true
//    val FIRST_FRAGMENT_TAG = "first_fragment"
//    val SECOND_FRAGMENT_TAG = "second_fragment"
//    val currentFragmentTag =
//        supportFragmentManager.findFragmentById(R.id.fragment_place_holder)?.tag

    //懒加载注入viewModel
    private val playerViewModel by lazy {
        ViewModelProvider(this)[MusicPlayerViewModel::class.java]
    }

    //懒加载注入viewBinding
    private val mBinding: ActivityPlayerBinding by lazy {
        ActivityPlayerBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        iniTab()
        iniBind()
        replaceFragment(PageOneFragment())
        iniClick()
        playerViewModel.getMusicLyrics("33894312")
    }

    /**
     * 下面是点击事件
     *
     */
    private fun iniClick() {
        /**
         * 点击我们的制定区域，切换不同的fragment
         */
        mBinding.fragmentPlaceHolder.setOnClickListener {
            replaceFragment(PageTwoFragment())
        }

    }

    /**
     * 切换fragment
     */

    @SuppressLint("CommitTransaction", "ResourceType")
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        // 设置自定义动画
        transaction.setCustomAnimations(
            R.anim.fade_in,  // 新的Fragment进入动画
            R.anim.fade_out,  // 旧的Fragment退出动画
            R.anim.fade_in,  // 新的Fragment进入动画
            R.anim.fade_out,
        )
        transaction.replace(R.id.fragment_place_holder, fragment)
        // 将当前 Fragment 添加到返回栈中
        transaction.addToBackStack(null)
        transaction.commit()

    }

    /**
     * 开启沉浸式状态栏
     */
    private fun iniTab() {
        transparentStatusBar(window, false)

    }


    @SuppressLint("SetTextI18n")
    private fun iniBind() {
        playerViewModel.getMusicUrl("2059741543")
        playerViewModel.getMusicInformation("1390520625")
        playerViewModel.musicInfo.observe(this) {
            mBinding.tvMusicTitle.text = it[0].name
            when (it[0].ar.size) {
                1 -> mBinding.tvArtistName.text = it[0].ar[0].name
                2 -> mBinding.tvArtistName.text = it[0].ar[0].name + "/" + it[0].ar[1].name
            }

        }

    }


    /**
     * 播放音乐
     */
//    private fun playMusic() {
//        val mPlayer = ExoPlayer.Builder(this).build()
//        mBinding.exoPlayer.player=mPlayer
//        playerViewModel.musicUrlInfo.observe(this){
//            val url= it[0].url
//            Log.d("playMusic","(PlayerActivity.kt:47)-->> ${it[0].url}");
//            val mediaItem = MediaItem.fromUri(Uri.parse(url))
//            mPlayer.setMediaItem(mediaItem)//准备媒体资源
//            mPlayer.prepare()
//            mPlayer.play()//开始播放
//        }
//
//
//
//
//
//    }


}