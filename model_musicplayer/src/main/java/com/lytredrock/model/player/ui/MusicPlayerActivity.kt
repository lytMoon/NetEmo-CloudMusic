package com.lytredrock.model.player.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
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


@Route(path = "/music/musicPlay")
class MusicPlayerActivity : AppCompatActivity(), View.OnClickListener {

    @Autowired
    lateinit var musicId: String
    private val musicProgressData: MutableLiveData<MusicProgressData> = MutableLiveData()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mBinder: MusicService.MusicBinder
    private val connection = object : ServiceConnection {
        /**
         * activity 和 service 成功绑定的时候会调用
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            //初始化mBinder对象
            mBinder = service as MusicService.MusicBinder
            mBinder.startPlay()
            handler.postDelayed(runnable, 1000)
        }

        //service进程创建过程中崩溃或者被杀掉的时候会调用
        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }

    val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            updateProgress()
            // 继续安排下一次定时任务
            handler.postDelayed(this, 1000) // 每隔1秒执行一次
        }
    }

    private fun updateProgress() {
        val currentPosition = mBinder.getCurrentPosition()
        val bufferedPosition = mBinder.getBufferedPosition()
        val duration = mBinder.getDuration()
        Log.d("currentPlayer", "(MusicPlayerActivity.kt:48)-->> $duration")
        updateData(currentPosition, bufferedPosition, duration)
    }

    //懒加载注入viewModel和viewBinding
    private val playerViewModel by lazy {
        ViewModelProvider(this)[MusicPlayerViewModel::class.java]
    }
    private val mBinding: ActivityPlayerBinding by lazy {
        ActivityPlayerBinding.inflate(
            layoutInflater
        )
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        ARouter.getInstance().inject(this@MusicPlayerActivity)
        iniSp()
        transparentStatusBar(window, false)
        replaceFragment(PageOneFragment())
        identifyNotify(this)
        iniUpData()//这个方法里面使用viewModel接受网络请求的道的数据，在fragment里面可以被观察到
        iniSeekBar()//同步进度条
        iniClick()//实现点击方法
        iniSendMsg()//把音乐放在service里面播放
    }

    private fun iniSp() {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        if (::musicId.isInitialized) {
            Log.d("852852", "(MusicPlayerActivity.kt:106)-->> 非空");
            val editor = sharedPreferences.edit()
            editor.putString("my_key", musicId)
            editor.apply()
        }
    }

    private fun iniSendMsg() {
        val intent = Intent(this, MusicService::class.java)
        playerViewModel.musicUrlInfo.observe(this) {
            // 存储数据
            intent.putExtra("musicUrl", it[0].url)
            Log.d("musicUrl", "(MusicPlayerActivity.kt:118)-->> ${it[0].url}");
            startService(intent)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

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


    /**
     * 这里实现点击事件,分别是播放暂停的切换和初始化fragment
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.music_play -> {
                if (mBinder.isPlaying()) {
                    mBinding.musicPlay.setImageDrawable(getDrawable(R.drawable.play_icon))
                    mBinder.stop()

                } else {
                    mBinding.musicPlay.setImageDrawable(getDrawable(R.drawable.pause_icon))
                    mBinder.start()
                }
            }

            R.id.fragment_place_holder -> replaceFragment(PageTwoFragment())

            R.id.iv_share -> {
                myToast("分享", this)
                //下面是采用系统的分享工具
                playerViewModel.musicUrlInfo.observe(this) {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"//指定类型是纯文本分享
                    intent.putExtra(Intent.EXTRA_SUBJECT, "音乐MV链接")
                    intent.putExtra(Intent.EXTRA_TEXT, it[0].url)
                    startActivity(Intent.createChooser(intent, "分享到"))
                }
            }

            R.id.iv_exist -> {
                myToast("已经切换到后台服务", this)
                finish()
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
                val position = ((seekBar?.progress)?.times(mBinder.getDuration()) ?: 1) / 100
                mBinder.seekTo(position.toLong())
                mBinder.start()
                mBinding.musicPlay.setImageDrawable(getDrawable(R.drawable.pause_icon))
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
        if (!::musicId.isInitialized) {
            Log.d("852852", "(MusicPlayerActivity.kt:112)-->> 空");
            val music = sharedPreferences.getString("my_key", "").toString()
            Log.d("852852", "(MusicPlayerActivity.kt:109)-->> $music")
            playerViewModel.getMusicComments(music)
            playerViewModel.getMusicLyrics(music)
            playerViewModel.getMusicUrl(music)
            playerViewModel.getMusicInformation(music)
            playerViewModel.musicInfo.observe(this) {
                mBinding.tvMusicTitle.text = it[0].name
                when (it[0].ar.size) {
                    1 -> mBinding.tvArtistName.text = it[0].ar[0].name
                    2 -> mBinding.tvArtistName.text = it[0].ar[0].name + "/" + it[0].ar[1].name
                }
            }
        } else {
            Log.d("iniUpData", "(MusicPlayerActivity.kt:240)-->> $musicId");
            playerViewModel.getMusicComments(musicId)
            playerViewModel.getMusicLyrics(musicId)
            playerViewModel.getMusicUrl(musicId)
            playerViewModel.getMusicInformation(musicId)
            playerViewModel.musicInfo.observe(this) {
                mBinding.tvMusicTitle.text = it[0].name
                when (it[0].ar.size) {
                    1 -> mBinding.tvArtistName.text = it[0].ar[0].name
                    2 -> mBinding.tvArtistName.text = it[0].ar[0].name + "/" + it[0].ar[1].name
                }
            }
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
     * finish方法里面释放我们的player和之前的handler
     */
    override fun finish() {
        handler.removeCallbacks(runnable)//释放我们的handler
        unbindService(connection)//解除绑定
        super.finish()
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