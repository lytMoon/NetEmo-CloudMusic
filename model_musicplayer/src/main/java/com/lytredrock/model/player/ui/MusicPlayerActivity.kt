package com.lytredrock.model.player.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.lytredrock.lib.base.BaseUtils
import com.lytredrock.lib.base.BaseUtils.myToast
import com.lytredrock.lib.base.BaseUtils.transparentStatusBar
import com.lytredrock.model.player.R
import com.lytredrock.model.player.databinding.ActivityPlayerBinding
import com.lytredrock.model.player.fragment.PageOneFragment
import com.lytredrock.model.player.fragment.PageTwoFragment
import com.lytredrock.model.player.playerData.MusicProgressData
import com.lytredrock.model.player.utils.ServiceUtils.identifyNotify
import com.lytredrock.model.player.viewmodel.MusicPlayerViewModel


class MusicPlayerActivity : AppCompatActivity(), View.OnClickListener {
    private val musicProgressData: MutableLiveData<MusicProgressData> = MutableLiveData()
    private var isMusicDisplayed = true
    private lateinit var player: ExoPlayer
    private lateinit var mBinder: MusicService.MusicBinder
    private val  connection = object :  ServiceConnection{



        /**
         * activity 和 service 成功绑定的时候会调用
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinder=service as MusicService.MusicBinder
         //   mBinder.startPlay()
            mBinder.getProgress()


        }

        /**
         * service进程创建过程中崩溃或者被杀掉的时候会调用
         */
        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }

    val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            // 获取MediaPlayer的当前播放进度
            val currentPlayer = player
            //获取进度并通知
            val currentPosition = currentPlayer.currentPosition.toInt()
            val bufferedPosition = currentPlayer.bufferedPosition.toInt()
            val duration = currentPlayer.duration.toInt()
            Log.d("currentPlayer", "(MusicPlayerActivity.kt:48)-->> $duration");
            updateData(currentPosition, bufferedPosition, duration)
            // 继续安排下一次定时任务
            handler.postDelayed(this, 1000) // 每隔1秒执行一次
        }
    }


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
        iniBind()
        transparentStatusBar(window, false)
        replaceFragment(PageOneFragment())
        identifyNotify(this)
        iniUpData()//这个方法里面使用viewModel接受网络请求的道的数据，在fragment里面可以被观察到
        iniSeekBar()
        iniClick()
        playMusic()
    }



    /**
     * 这里用来更新我们的seekBar
     */
    private fun iniSeekBar() {
        musicProgressData.observe(this) {
            Log.d(
                "musicProgressData",
                "(MusicPlayerActivity.kt:88)-->>${it.duration}+${it.bufferedPosition}+${it.currentPosition}"
            )
            mBinding.musicProgress.progress = (it.currentPosition * 100 / it.duration)
        }
    }

    private fun iniBind() {
        player = ExoPlayer.Builder(this).build()
        mBinding.playerView.player = player
    }


    /**
     * 这里实现点击事件,分别是播放暂停的切换和初始化fragment
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.music_play -> {
                if (isMusicDisplayed) {
                    mBinding.musicPlay.setImageDrawable(getDrawable(R.drawable.play_icon))
                    isMusicDisplayed = false
                    player.playWhenReady = false

                } else {
                    mBinding.musicPlay.setImageDrawable(getDrawable(R.drawable.pause_icon))
                    isMusicDisplayed = true
                    player.playWhenReady = true
                }
            }

            R.id.fragment_place_holder -> replaceFragment(PageTwoFragment())

            R.id.iv_share ->{
                BaseUtils.myToast("分享", this)
                //下面是采用系统的分享工具
                playerViewModel.musicUrlInfo.observe(this) {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"//指定类型是纯文本分享
                    intent.putExtra(Intent.EXTRA_SUBJECT, "音乐MV链接")
                    intent.putExtra(Intent.EXTRA_TEXT, it[0].url)
                    startActivity(Intent.createChooser(intent, "分享到"))
                }
            }

            R.id.iv_exist ->{
                myToast("已经切换到后台服务",this)
                val intent = Intent(this,MusicService::class.java)
                var  url: String? = null
                var  currentPosition: Int? = null
                playerViewModel.musicUrlInfo.observe(this){
                    url=it[0].url
                }
                musicProgressData.observe(this){
                    currentPosition=it.currentPosition
                }
                intent.putExtra("musicUrl",url)
                intent.putExtra("currentPosition",currentPosition)
                Log.d("musicUrl","(MusicPlayerActivity.kt:165)-->> $url");
                startService(intent)
                bindService(intent,connection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    /**
     * 下面是点击事件
     */
    private fun iniClick() {
        mBinding.musicPlay.setOnClickListener(this)
        mBinding.fragmentPlaceHolder.setOnClickListener(this)
        mBinding.ivShare.setOnClickListener(this)
        mBinding.ivExist.setOnClickListener(this)
        /**
         * 设置seekBar和音乐的联动
         *
         */
        mBinding.musicProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (!player.playWhenReady) {
                    val position = ((seekBar?.progress)?.times(player.duration) ?: 1) / 100
                    player.seekTo(position)
                    mBinding.musicPlay.setImageDrawable(getDrawable(R.drawable.pause_icon))
                    isMusicDisplayed = true
                    player.playWhenReady = true
                } else {
                    val position = ((seekBar?.progress)?.times(player.duration) ?: 1) / 100
                    player.seekTo(position)
                }
            }
        })
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

        )
        transaction.replace(R.id.fragment_place_holder, fragment)
        transaction.commit()

    }

    /**
     * 开启沉浸式状态栏
     */



    @SuppressLint("SetTextI18n")
    private fun iniUpData() {
        /**
         * 分别获取  评论 歌词 播放链接 音乐的信息
         */
        playerViewModel.getMusicComments("1403318151")
        playerViewModel.getMusicLyrics("1403318151")
        playerViewModel.getMusicUrl("1403318151")
        playerViewModel.getMusicInformation("1403318151")
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
    private fun playMusic() {
        val player = mBinding.playerView.player
        playerViewModel.musicUrlInfo.observe(this) {
            val url = it[0].url
            Log.d("playMusic", "(PlayerActivity.kt:47)-->> ${it[0].url}");
            //填充媒体数据
            player?.addMediaItem(MediaItem.fromUri(url))
            //准备播放
            player?.prepare()
            //准备完成就开始播放
            if (player != null) {
                player.playWhenReady = true
            }
            // 启动定时任务，每隔1秒更新一次播放进度
            handler.postDelayed(runnable, 1000)
        }


    }


    /**
     * 这里我们使用liveData更新数据，每次都通过新的数据类进行过滤。最终把更新的数据值传递到我们的liveData里面
     */
    fun updateData(currentPosition: Int, bufferedPosition: Int, duration: Int) {
        val newData = MusicProgressData(currentPosition, bufferedPosition, duration)
        musicProgressData.value = newData
    }


    /**
     * 这个是测试用的
     */
    override fun onPause() {
        super.onPause()
        player.stop()
        player.release()
    }

    /**
     * finish方法里面释放我们的player和之前的handler
     */
    override fun finish() {
        super.finish()
        handler.removeCallbacks(runnable)//释放我们的handler
        player.stop()
        player.release()
        unbindService(connection)//解除绑定

    }

}


/**
 * 下面的代码无用
 * 本来准备设置标记符，但是当你切换到第二个的时候，点击事件被vp拦截了，再次点击的时候不会切换回来（除非vp为空，所以放弃了这种判断方法）
 */


//    设置要切换fragment的标记符
//    private var isFragmentOneDisplayed = true
//    val FIRST_FRAGMENT_TAG = "first_fragment"
//    val SECOND_FRAGMENT_TAG = "second_fragment"
//    val currentFragmentTag =
//        supportFragmentManager.findFragmentById(R.id.fragment_place_holder)?.tag